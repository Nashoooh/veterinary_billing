package com.example.veterinary_billing.repository;

import com.example.veterinary_billing.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByFacturaId(Long facturaId); // Consulta personalizada para obtener servicios por factura
}