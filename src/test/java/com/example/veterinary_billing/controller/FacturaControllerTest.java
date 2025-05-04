package com.example.veterinary_billing.controller;

import com.example.veterinary_billing.dto.FacturaConServiciosDTO;
import com.example.veterinary_billing.dto.ServicioDTO;
import com.example.veterinary_billing.exception.ServiceNotFoundException;
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
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @MockBean
    private ServicioService servicioService;

    private Factura factura1;
    private Servicio servicio1;
    private Servicio servicio2;
    private ServicioDTO servicioDTO1;
    private ServicioDTO servicioDTO2;
    private FacturaConServiciosDTO facturaConServiciosDTO;

    @BeforeEach
    void setUp() {
        factura1 = new Factura(1L, "Tarjeta", 10000, LocalDate.now(), false); // ← ajustado
    
        servicio1 = new Servicio("Chequeo general", 1500, "Chequeo general", factura1);
        servicio2 = new Servicio("Vacunación básica", 20000, "Vacunación básica", factura1);
    
        servicioDTO1 = new ServicioDTO(1L, "Consulta", "Chequeo general", 50);
        servicioDTO2 = new ServicioDTO(2L, "Vacuna", "Vacunación básica", 30);
    
        facturaConServiciosDTO = new FacturaConServiciosDTO(
            1L, "Tarjeta", 10000, LocalDate.now(), false,
            Arrays.asList(servicioDTO1, servicioDTO2)
        );
    
        servicio1.setFactura(factura1);
        servicio2.setFactura(factura1);
    }

    @Test
    void testCrearFactura() throws Exception {
        when(facturaService.addInvoice(any(Factura.class))).thenReturn(factura1);

        mockMvc.perform(post("/facturas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"metodoPago\":\"Tarjeta\",\"montoTotal\":10000,\"fecha\":\"2023-12-15\",\"pagado\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.metodoPago").value("Tarjeta"))
                .andExpect(jsonPath("$.montoTotal").value(10000));

        verify(facturaService, times(1)).addInvoice(any(Factura.class));
    }

    @Test
    void testObtenerFacturas() throws Exception {
        List<Factura> facturas = Collections.singletonList(factura1);
        when(facturaService.getAllInvoices()).thenReturn(facturas);

        mockMvc.perform(get("/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].metodoPago").value("Tarjeta"));

        verify(facturaService, times(1)).getAllInvoices();
    }


}