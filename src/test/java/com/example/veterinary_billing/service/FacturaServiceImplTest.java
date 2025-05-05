package com.example.veterinary_billing.service;

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
    private FacturaRepository facturaRepository; // Mock del repositorio de facturas

    @InjectMocks
    private FacturaServiceImpl facturaService; // Servicio que se está probando, con dependencias inyectadas

    private Factura factura1;
    private Factura factura2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada prueba

        // Configuración de objetos de prueba
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
        // Configuración del mock: cuando se llame a findAll, devolverá una lista con factura1 y factura2
        when(facturaRepository.findAll()).thenReturn(Arrays.asList(factura1, factura2));

        // Llamada al método que se está probando
        List<Factura> result = facturaService.getAllInvoices();

        // Verificaciones
        assertNotNull(result); // Verifica que el resultado no sea nulo
        assertEquals(2, result.size()); // Verifica que la lista tenga 2 elementos
        assertEquals(150.0, result.get(0).getMontoTotal()); // Verifica que el monto total de la primera factura sea 150.0
        verify(facturaRepository, times(1)).findAll(); // Verifica que el método findAll del repositorio se haya llamado exactamente una vez
    }

    // Test: Obtener factura por ID existente
    @Test
    void testGetInvoiceById() {
        // Configuración del mock: cuando se llame a findById con ID 1, devolverá factura1
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura1));

        // Llamada al método que se está probando
        Factura result = facturaService.getInvoiceById(1L);

        // Verificaciones
        assertNotNull(result); // Verifica que el resultado no sea nulo
        assertEquals("Tarjeta", result.getMetodoPago()); // Verifica que el método de pago sea "Tarjeta"
        verify(facturaRepository, times(1)).findById(1L); // Verifica que el método findById del repositorio se haya llamado exactamente una vez con el ID 1
    }
}