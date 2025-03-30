package com.example.veterinary_billing.service;

import com.example.veterinary_billing.model.Invoice;
import com.example.veterinary_billing.model.ServiceProvided;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {
    private final List<Invoice> invoices = new ArrayList<>();
    private int nextId = 1;

    // Registrar un nuevo servicio en una factura
    public Invoice registerService(int invoiceId, ServiceProvided service) {
        Invoice invoice = findInvoiceById(invoiceId);
        if (invoice == null) {
            invoice = new Invoice(nextId++);
            invoices.add(invoice);
        }
        invoice.addService(service);
        return invoice;
    }

    // Consultar todas las facturas
    public List<Invoice> getAllInvoices() {
        return invoices;
    }

    // Consultar una factura por ID
    public Invoice getInvoiceById(int id) {
        return findInvoiceById(id);
    }

    // Pagar una factura
    public void payInvoice(int id) {
        Invoice invoice = findInvoiceById(id);
        if (invoice != null) {
            invoice.markAsPaid();
        }
    }

    // Método auxiliar para encontrar una factura por ID
    private Invoice findInvoiceById(int id) {
        Optional<Invoice> invoice = invoices.stream()
                .filter(i -> i.getId() == id)
                .findFirst();
        return invoice.orElse(null);
    }
}