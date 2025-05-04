package com.example.veterinary_billing.service;

import com.example.veterinary_billing.exception.InvoiceNotFoundException;
import com.example.veterinary_billing.model.Factura;
import com.example.veterinary_billing.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FacturaServiceImplTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaServiceImpl facturaService;

    private Factura factura1;
    private Factura factura2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar objetos de prueba
        factura1 = new Factura();
        factura1.setId((long) 1);
        factura1.setFecha(LocalDate.of(2024, 5, 10));
        factura1.setMontoTotal((int) 150.0);
        factura1.setMetodoPago("Tarjeta");
        factura1.setPagado(true);

        factura2 = new Factura();
        factura2.setId((long) 2);
        factura2.setFecha(LocalDate.of(2024, 5, 12));
        factura2.setMontoTotal((int) 80.0);
        factura2.setMetodoPago("Efectivo");
        factura2.setPagado(false);
    }

    // Test: Obtener todas las facturas
    @Test
    void testGetAllInvoices() {
        when(facturaRepository.findAll()).thenReturn(Arrays.asList(factura1, factura2));

        List<Factura> result = facturaService.getAllInvoices();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(150.0, result.get(0).getMontoTotal());
        verify(facturaRepository, times(1)).findAll();
    }

    // Test: Obtener factura por ID existente
    @Test
    void testGetInvoiceById() {
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura1));

        Factura result = facturaService.getInvoiceById(1L);

        assertNotNull(result);
        assertEquals("Tarjeta", result.getMetodoPago());
        verify(facturaRepository, times(1)).findById(1L);
    }


}