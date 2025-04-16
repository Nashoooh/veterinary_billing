package com.example.veterinary_billing.service;

import com.example.veterinary_billing.model.Factura;
import com.example.veterinary_billing.exception.InvoiceNotFoundException;
import com.example.veterinary_billing.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public List<Factura> getAllInvoices() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura getInvoiceById(Long id) {
        return facturaRepository.findById(id)
                .orElseThrow(() -> new InvoiceNotFoundException("Factura no encontrada con ID: " + id));
    }

    @Override
    public Factura addInvoice(Factura factura) {
        return facturaRepository.save(factura);
    }

    @Override
    public Factura updateInvoice(Long id, Factura factura) {
        Factura facturaToUpdate = getInvoiceById(id);
        facturaToUpdate.setMetodoPago(factura.getMetodoPago());
        facturaToUpdate.setMontoTotal(factura.getMontoTotal());
        facturaToUpdate.setFecha(factura.getFecha());
        facturaToUpdate.setPagado(factura.isPagado());
        return facturaRepository.save(facturaToUpdate);
    }

    @Override
    public void deleteInvoice(Long id) {
        if (!facturaRepository.existsById(id)) {
            throw new InvoiceNotFoundException("Factura no encontrada con ID: " + id);
        }
        facturaRepository.deleteById(id);
    }
}