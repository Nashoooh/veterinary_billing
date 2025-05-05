package com.example.veterinary_billing.controller;

import com.example.veterinary_billing.dto.FacturaConServiciosDTO;
import com.example.veterinary_billing.dto.ServicioDTO;
import com.example.veterinary_billing.model.Factura;
import com.example.veterinary_billing.model.Servicio;
import com.example.veterinary_billing.service.FacturaService;
import com.example.veterinary_billing.service.ServicioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc se utiliza para simular solicitudes HTTP al controlador

    @MockBean
    private FacturaService facturaService; // Mock del servicio de facturas

    @MockBean
    private ServicioService servicioService; // Mock del servicio de servicios

    private Factura factura1;
    private Servicio servicio1;
    private Servicio servicio2;
    private ServicioDTO servicioDTO1;
    private ServicioDTO servicioDTO2;
    private FacturaConServiciosDTO facturaConServiciosDTO;

    @BeforeEach
    void setUp() {
        // Configuración inicial de los objetos necesarios para las pruebas
        factura1 = new Factura(1L, "Tarjeta", 10000, LocalDate.now(), false); // Factura de prueba
    
        servicio1 = new Servicio("Chequeo general", 1500, "Chequeo general", factura1); // Servicio asociado a la factura
        servicio2 = new Servicio("Vacunación básica", 20000, "Vacunación básica", factura1); // Otro servicio asociado
    
        servicioDTO1 = new ServicioDTO(1L, "Consulta", "Chequeo general", 50); // DTO para representar un servicio
        servicioDTO2 = new ServicioDTO(2L, "Vacuna", "Vacunación básica", 30); // Otro DTO de servicio
    
        // DTO que combina una factura con sus servicios
        facturaConServiciosDTO = new FacturaConServiciosDTO(
            1L, "Tarjeta", 10000, LocalDate.now(), false,
            Arrays.asList(servicioDTO1, servicioDTO2)
        );
    
        // Asociar los servicios a la factura
        servicio1.setFactura(factura1);
        servicio2.setFactura(factura1);
    }

    @Test
    void testCrearFactura() throws Exception {
        // Configuración del mock: cuando se llame a addInvoice, devolverá factura1
        when(facturaService.addInvoice(any(Factura.class))).thenReturn(factura1);

        // Simula una solicitud POST para crear una factura
        mockMvc.perform(post("/facturas") // Endpoint que se está probando
                .contentType(MediaType.APPLICATION_JSON) // Tipo de contenido enviado
                .content("{\"metodoPago\":\"Tarjeta\",\"montoTotal\":10000,\"fecha\":\"2023-12-15\",\"pagado\":false}")) // Datos de la factura en formato JSON
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta")) // Verifica que el campo metodoPago sea "Tarjeta"
                .andExpect(jsonPath("$.montoTotal").value(10000)); // Verifica que el campo montoTotal sea 10000

        // Verifica que el método addInvoice del servicio se haya llamado exactamente una vez
        verify(facturaService, times(1)).addInvoice(any(Factura.class));
    }

    @Test
    void testObtenerFacturas() throws Exception {
        // Configuración del mock: cuando se llame a getAllInvoices, devolverá una lista con factura1
        List<Factura> facturas = Collections.singletonList(factura1);
        when(facturaService.getAllInvoices()).thenReturn(facturas);

        // Simula una solicitud GET para obtener todas las facturas
        mockMvc.perform(get("/facturas")) // Endpoint que se está probando
                .andExpect(status().isOk()) // Verifica que el estado de la respuesta sea 200 OK
                .andExpect(jsonPath("$[0].metodoPago").value("Tarjeta")); // Verifica que el primer elemento tenga metodoPago "Tarjeta"

        // Verifica que el método getAllInvoices del servicio se haya llamado exactamente una vez
        verify(facturaService, times(1)).getAllInvoices();
    }
}