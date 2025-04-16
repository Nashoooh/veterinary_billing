package com.example.veterinary_billing.repository;

import com.example.veterinary_billing.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    // No se necesitan métodos adicionales si solo usas las operaciones CRUD básicas
}