package com.example.veterinary_billing.controller;

import com.example.veterinary_billing.dto.FacturaConServiciosDTO;
import com.example.veterinary_billing.dto.ServicioDTO;
import com.example.veterinary_billing.exception.ServiceNotFoundException;
import com.example.veterinary_billing.model.Factura;
import com.example.veterinary_billing.model.Servicio;
import com.example.veterinary_billing.service.FacturaService;
import com.example.veterinary_billing.service.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private ServicioService servicioService;

    // POST /facturas -> Crear una nueva factura
    @PostMapping
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        Factura nuevaFactura = facturaService.addInvoice(factura);
        return ResponseEntity.ok(nuevaFactura);
    }
    
    // GET /facturas -> Obtener todas las facturas
    @GetMapping
    public ResponseEntity<List<Factura>> obtenerFacturas() {
        List<Factura> facturas = facturaService.getAllInvoices();
        return ResponseEntity.ok(facturas);
    }

    // POST /facturas/{idFactura}/servicios -> Agregar servicio a una factura
    @PostMapping("/{idFactura}/servicios")
    public ResponseEntity<FacturaConServiciosDTO> agregarServicio(@PathVariable Long idFactura, @RequestBody Servicio servicio) {
        // Obtener la factura por ID
        Factura factura = facturaService.getInvoiceById(idFactura);
    
        // Asociar la factura al servicio
        servicio.setFactura(factura);
    
        // Guardar el nuevo servicio
        servicioService.addService(servicio);
    
        // Obtener todos los servicios asociados a la factura desde el repositorio
        List<Servicio> servicios = servicioService.getServicesByInvoiceId(idFactura);
    
        // Convertir los servicios a DTOs
        List<ServicioDTO> serviciosDTO = servicios.stream()
                .map(s -> new ServicioDTO(s.getId(), s.getNombreServicio(), s.getDescripcion(), s.getCosto()))
                .toList();
    
        // Crear el DTO de respuesta
        FacturaConServiciosDTO facturaConServiciosDTO = new FacturaConServiciosDTO(
                factura.getId(),
                factura.getMetodoPago(),
                factura.getMontoTotal(),
                factura.getFecha(),
                factura.isPagado(),
                serviciosDTO
        );
    
        return ResponseEntity.ok(facturaConServiciosDTO);
    }

    // GET /invoices/{id} -> Obtener una factura por ID
    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerFacturaPorId(@PathVariable Long id) {
        Factura factura = facturaService.getInvoiceById(id);
        return ResponseEntity.ok(factura);
    }

    // POST /invoices/{id}/pay -> Marcar una factura como pagada
    @PostMapping("/{id}/pagar")
    public ResponseEntity<Factura> pagarFactura(@PathVariable Long id) {
        Factura factura = facturaService.getInvoiceById(id);
        factura.setPagado(true);
        Factura facturaActualizada = facturaService.addInvoice(factura);
        return ResponseEntity.ok(facturaActualizada);
    }

    // GET /facturas/{idFactura}/servicios -> Obtener los servicios de una factura
    @GetMapping("/{idFactura}/servicios")
    public ResponseEntity<List<ServicioDTO>> obtenerServiciosPorFactura(@PathVariable Long idFactura) {
        // Obtener los servicios asociados a la factura
        List<Servicio> servicios = servicioService.getServicesByInvoiceId(idFactura);

        // Convertir los servicios a DTOs
        List<ServicioDTO> serviciosDTO = servicios.stream()
                .map(servicio -> new ServicioDTO(
                        servicio.getId(),
                        servicio.getNombreServicio(),
                        servicio.getDescripcion(),
                        servicio.getCosto()
                ))
                .toList();

        return ResponseEntity.ok(serviciosDTO);
    }

    // DELETE /facturas/{idFactura}/servicios -> Obtener los servicios de una factura
    @DeleteMapping("/{idFactura}/servicios/{idServicio}")
    public ResponseEntity<Map<String, Object>> eliminarServicio(@PathVariable Long idFactura, @PathVariable Long idServicio) {
        try {
            // Obtener el servicio por ID
            Servicio servicio = servicioService.getServiceById(idServicio);
    
            // Validar que el servicio pertenece a la factura especificada
            if (!servicio.getFactura().getId().equals(idFactura)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "El servicio no pertenece a la factura especificada."
                ));
            }
    
            // Eliminar el servicio
            servicioService.deleteService(idServicio);
    
            // Recalcular el monto total de la factura
            servicioService.recalculateInvoiceTotal(idFactura);
    
            // Retornar un JSON con éxito
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "El servicio fue eliminado correctamente."
            ));
        } catch (ServiceNotFoundException e) {
            // Manejar el caso en que el servicio no exista
            return ResponseEntity.status(404).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            // Manejar cualquier otro error inesperado
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Ocurrió un error inesperado al eliminar el servicio."
            ));
        }
    }
    
    // PUT /facturas/{idFactura}/servicios/{idServicio} -> Actualizar un servicio asociado a una factura
    @PutMapping("/{idFactura}/servicios/{idServicio}")
    public ResponseEntity<Map<String, Object>> actualizarServicio(
            @PathVariable Long idFactura,
            @PathVariable Long idServicio,
            @RequestBody Servicio servicioActualizado) {
        try {
            // Obtener el servicio por ID
            Servicio servicioExistente = servicioService.getServiceById(idServicio);
    
            // Validar que el servicio pertenece a la factura especificada
            if (!servicioExistente.getFactura().getId().equals(idFactura)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "El servicio no pertenece a la factura especificada."
                ));
            }
    
            // Obtener la factura asociada al servicio
            Factura factura = servicioExistente.getFactura();
    
            // Actualizar el montoTotal de la factura si el costo del servicio cambia
            if (servicioExistente.getCosto() != servicioActualizado.getCosto()) {
                int diferenciaCosto = servicioActualizado.getCosto() - servicioExistente.getCosto();
                factura.setMontoTotal(factura.getMontoTotal() + diferenciaCosto);
                facturaService.addInvoice(factura);
            }
    
            // Actualizar el servicio utilizando el método del servicio
            servicioService.updateService(idServicio, servicioActualizado);
    
            // Retornar un JSON con éxito
            return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "El servicio fue actualizado correctamente."
            ));
        } catch (ServiceNotFoundException e) {
            // Manejar el caso en que el servicio no exista
            return ResponseEntity.status(404).body(Map.of(
                "success", false,
                "message", e.getMessage()
            ));
        } catch (Exception e) {
            // Manejar cualquier otro error inesperado
            return ResponseEntity.status(500).body(Map.of(
                "success", false,
                "message", "Ocurrió un error inesperado al actualizar el servicio."
            ));
        }
    }

    
}