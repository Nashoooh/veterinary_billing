package com.example.veterinary_billing.service;

import com.example.veterinary_billing.model.Factura;
import com.example.veterinary_billing.model.Servicio;
import com.example.veterinary_billing.exception.ServiceNotFoundException;
import com.example.veterinary_billing.repository.ServicioRepository;
import com.example.veterinary_billing.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioServiceImpl implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<Servicio> getAllServices() {
        return servicioRepository.findAll();
    }

    @Override
    public Servicio getServiceById(Long id) {
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException("Servicio no encontrado con ID: " + id));
    }

    @Override
    public Servicio addService(Servicio servicio) {
        // Obtener la factura asociada al servicio
        Factura factura = servicio.getFactura();

        if (factura == null) {
            throw new IllegalArgumentException("El servicio debe estar asociado a una factura.");
        }

        // Actualizar el montoTotal de la factura
        factura.setMontoTotal(factura.getMontoTotal() + servicio.getCosto());

        // Guardar la factura actualizada
        facturaRepository.save(factura);

        // Guardar el servicio
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio updateService(Long id, Servicio servicio) {
        // Obtener el servicio existente por ID
        Servicio servicioToUpdate = getServiceById(id);
    
        // Actualizar solo los campos necesarios
        if (servicio.getNombreServicio() != null) {
            servicioToUpdate.setNombreServicio(servicio.getNombreServicio());
        }
        if (servicio.getCosto() != 0) { // Asumiendo que el costo no puede ser 0 a menos que sea intencional
            servicioToUpdate.setCosto(servicio.getCosto());
        }
        if (servicio.getDescripcion() != null) {
            servicioToUpdate.setDescripcion(servicio.getDescripcion());
        }
    
        // No actualizar la factura si es nula
        if (servicio.getFactura() != null) {
            servicioToUpdate.setFactura(servicio.getFactura());
        }
    
        // Guardar el servicio actualizado
        return servicioRepository.save(servicioToUpdate);
    }

    @Override
    public void deleteService(Long id) {
        if (!servicioRepository.existsById(id)) {
            throw new ServiceNotFoundException("Servicio no encontrado con ID: " + id);
        }
        servicioRepository.deleteById(id);
    }

    @Override
    public List<Servicio> getServicesByInvoiceId(Long invoiceId) {
        return servicioRepository.findByFacturaId(invoiceId);
    }

    public void recalculateInvoiceTotal(Long facturaId) {
        // Obtener la factura por ID
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura no encontrada con ID: " + facturaId));
    
        // Obtener todos los servicios asociados a la factura
        List<Servicio> servicios = servicioRepository.findByFacturaId(facturaId);
    
        // Recalcular el monto total sumando los costos de los servicios
        double nuevoMontoTotal = servicios.stream()
                                          .mapToDouble(Servicio::getCosto)
                                          .sum();
    
        // Actualizar el monto total de la factura
        factura.setMontoTotal((int) nuevoMontoTotal);
    
        // Guardar la factura actualizada
        facturaRepository.save(factura);
    }

}