package com.example.veterinary_billing.service;

import com.example.veterinary_billing.exception.EventNotFoundException;
import com.example.veterinary_billing.model.Invoice;
import com.example.veterinary_billing.model.ServiceProvided;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillingService {
    private final List<Invoice> invoices = new ArrayList<>();
    private final List<ServiceProvided> services = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Crear servicios predeterminados
        services.add(new ServiceProvided("Consulta General", 15000));
        services.add(new ServiceProvided("Vacunación", 10000));
        services.add(new ServiceProvided("Baño y Peluquería", 8000));
        services.add(new ServiceProvided("Desparasitación", 5000));
        services.add(new ServiceProvided("Cirugía Menor", 30000));

        // Crear facturas y asociar servicios
        Invoice invoice1 = new Invoice(1, "Efectivo");
        invoice1.addService(services.get(0)); // Consulta General
        invoice1.addService(services.get(2)); // Baño y Peluquería

        Invoice invoice2 = new Invoice(2, "Tarjeta de Crédito");
        invoice2.addService(services.get(1)); // Vacunación
        invoice2.addService(services.get(3)); // Desparasitación

        Invoice invoice3 = new Invoice(3, "Efectivo");
        invoice3.addService(services.get(0)); // Consulta General
        invoice3.addService(services.get(4)); // Cirugía Menor

        // Agregar facturas a la lista
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
    }

    // Metodo para obtener todas las facturas existentes
    public List<Invoice> getAllInvoices() {
        return invoices;
    }

    // Metodo para buscar una factura por ID
    public Invoice getInvoiceById(int id) {
        return invoices.stream()
                .filter(invoice -> invoice.getId() == id) // Filtra las facturas por ID
                .findFirst() // Obtiene la primera que tenga el ID indicado
                .orElseThrow(() -> new EventNotFoundException("Factura no encontrado con ID: " + id)); // Si no la encuentra lanza la excepción
    }

    // Obtener servicios de un ID factura en particular
    public List<ServiceProvided> getServicesByInvoiceId(int invoiceId) {
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice != null) {
            return invoice.getServiceProvideds();
        }
        return new ArrayList<>();
    }

}
