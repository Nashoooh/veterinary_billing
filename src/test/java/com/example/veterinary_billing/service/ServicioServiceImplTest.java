package com.example.veterinary_billing.service;

import com.example.veterinary_billing.exception.ServiceNotFoundException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioServiceImplTest {

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private ServicioServiceImpl servicioService;

    private Factura factura1;
    private Servicio servicio1;
    private Servicio servicio2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar objetos de prueba
        factura1 = new Factura();
        factura1.setId(1L);
        factura1.setMontoTotal(0); // Será actualizado al añadir servicios

        servicio1 = new Servicio();
        servicio1.setId(1L);
        servicio1.setNombreServicio("Consulta General");
        servicio1.setDescripcion("Revisión veterinaria básica");
        servicio1.setCosto(50);
        servicio1.setFactura(factura1);

        servicio2 = new Servicio();
        servicio2.setId(2L);
        servicio2.setNombreServicio("Vacunación");
        servicio2.setDescripcion("Aplicación de vacuna antirrábica");
        servicio2.setCosto(30);
        servicio2.setFactura(factura1);
    }

    // Test: Obtener todos los servicios
    @Test
    void testGetAllServices() {
        when(servicioRepository.findAll()).thenReturn(Arrays.asList(servicio1, servicio2));

        List<Servicio> result = servicioService.getAllServices();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Consulta General", result.get(0).getNombreServicio());
        verify(servicioRepository, times(1)).findAll();
    }

    // Test: Obtener servicio por ID existente
    @Test
    void testGetServiceById() {
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio1));

        Servicio result = servicioService.getServiceById(1L);

        assertNotNull(result);
        assertEquals("Consulta General", result.getNombreServicio());
        verify(servicioRepository, times(1)).findById(1L);
    }


}