package com.example.veterinary_billing.controller;

import com.example.veterinary_billing.model.Invoice;
import com.example.veterinary_billing.model.ServiceProvided;
import com.example.veterinary_billing.service.BillingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService; // Se declara el servicio para utilizar sus métodos

    // Constructor del servicio para utilizar sus métodos
    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    // Consultar todas las facturas
    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        List<Invoice> events = billingService.getAllInvoices();
        if (events.isEmpty()) {
            // Devuelve una lista vacía con código 200 OK en caso de que no haya facturas registradas
            return ResponseEntity.ok(events);
        }
        return ResponseEntity.ok(events);
    }

    // Consultar una factura por ID
    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable String id) {
        Invoice invoice = billingService.getInvoiceById(id);
        // Devuelve la factura con código 200 OK
        return ResponseEntity.ok(invoice);
    }

    // Consultar los servicios de una factura por ID
    @GetMapping("/invoices/{id}/services")
    public ResponseEntity<List<ServiceProvided>> getServicesByInvoiceId(@PathVariable String id) {
        List<ServiceProvided> services = billingService.getServicesByInvoiceId(id);
        // Devuelve una lista vacía con código 200 OK (existe la factura pero no tiene servicios asociados)
        return ResponseEntity.ok(services);
    }
}
