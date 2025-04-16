package com.example.veterinary_billing.service;

import com.example.veterinary_billing.model.Servicio;
import java.util.List;

public interface ServicioService {
    List<Servicio> getAllServices();
    Servicio getServiceById(Long id);
    Servicio addService(Servicio servicio);
    Servicio updateService(Long id, Servicio servicio);
    void deleteService(Long id);
    List<Servicio> getServicesByInvoiceId(Long invoiceId);
    void recalculateInvoiceTotal(Long facturaId);

}