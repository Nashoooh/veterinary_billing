package com.example.veterinary_billing.service;

import com.example.veterinary_billing.model.Factura;
import com.example.veterinary_billing.model.Servicio;
import com.example.veterinary_billing.repository.ServicioRepository;
import com.example.veterinary_billing.repository.FacturaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioServiceImplTest {

    @Mock
    private ServicioRepository servicioRepository; // Mock del repositorio de servicios

    @Mock
    private FacturaRepository facturaRepository; // Mock del repositorio de facturas

    @InjectMocks
    private ServicioServiceImpl servicioService; // Servicio que se está probando, con dependencias inyectadas

    private Factura factura1;
    private Servicio servicio1;
    private Servicio servicio2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks antes de cada prueba

        // Inicializar objetos de prueba
        factura1 = new Factura();
        factura1.setId(1L);
        factura1.setMontoTotal(0); // Será actualizado al añadir servicios

        servicio1 = new Servicio();
        servicio1.setId(1L);
        servicio1.setNombreServicio("Consulta General");
        servicio1.setDescripcion("Revisión veterinaria básica");
        servicio1.setCosto(50);
        servicio1.setFactura(factura1); // Asociar el servicio a la factura

        servicio2 = new Servicio();
        servicio2.setId(2L);
        servicio2.setNombreServicio("Vacunación");
        servicio2.setDescripcion("Aplicación de vacuna antirrábica");
        servicio2.setCosto(30);
        servicio2.setFactura(factura1); // Asociar el servicio a la factura
    }

    // Test: Obtener todos los servicios
    @Test
    void testGetAllServices() {
        // Configuración del mock: cuando se llame a findAll, devolverá una lista con servicio1 y servicio2
        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio1, servicio2));

        // Llamada al método que se está probando
        List<Servicio> result = servicioService.getAllServices();

        // Verificaciones
        assertNotNull(result); // Verifica que el resultado no sea nulo
        assertEquals(2, result.size()); // Verifica que la lista tenga 2 elementos
        assertEquals("Consulta General", result.get(0).getNombreServicio()); // Verifica que el nombre del primer servicio sea "Consulta General"
        verify(servicioRepository, times(1)).findAll(); // Verifica que el método findAll del repositorio se haya llamado exactamente una vez
    }

    // Test: Obtener servicio por ID existente
    @Test
    void testGetServiceById() {
        // Configuración del mock: cuando se llame a findById con ID 1, devolverá servicio1
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio1));

        // Llamada al método que se está probando
        Servicio result = servicioService.getServiceById(1L);

        // Verificaciones
        assertNotNull(result); // Verifica que el resultado no sea nulo
        assertEquals("Consulta General", result.getNombreServicio()); // Verifica que el nombre del servicio sea "Consulta General"
        verify(servicioRepository, times(1)).findById(1L); // Verifica que el método findById del repositorio se haya llamado exactamente una vez con el ID 1
    }
}