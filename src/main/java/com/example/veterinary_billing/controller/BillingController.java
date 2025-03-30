package com.example.veterinary_billing.controller;

import com.example.veterinary_billing.model.Invoice;
import com.example.veterinary_billing.model.ServiceProvided;
import com.example.veterinary_billing.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    // Registrar un servicio en una factura
    @PostMapping("/register/{invoiceId}")
    public ResponseEntity<Invoice> registerService(@PathVariable int invoiceId, @Valid @RequestBody ServiceProvided service) {
        Invoice invoice = billingService.registerService(invoiceId, service);
        return ResponseEntity.ok(invoice);
    }

    // Consultar todas las facturas
    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        return ResponseEntity.ok(billingService.getAllInvoices());
    }

    // Consultar una factura por ID
    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable int id) {
        Invoice invoice = billingService.getInvoiceById(id);
        if (invoice != null) {
            return ResponseEntity.ok(invoice);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Pagar una factura
    @PostMapping("/pay/{id}")
    public ResponseEntity<Void> payInvoice(@PathVariable int id) {
        billingService.payInvoice(id);
        return ResponseEntity.ok().build();
    }
}
