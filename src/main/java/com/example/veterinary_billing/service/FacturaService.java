package com.example.veterinary_billing.service;

import com.example.veterinary_billing.model.Factura;
import java.util.List;

public interface FacturaService {
    List<Factura> getAllInvoices();
    Factura getInvoiceById(Long id);
    Factura addInvoice(Factura factura);
    Factura updateInvoice(Long id, Factura factura);
    void deleteInvoice(Long id);
}