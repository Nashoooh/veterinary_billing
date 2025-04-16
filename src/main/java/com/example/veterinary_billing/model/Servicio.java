package com.example.veterinary_billing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "SERVICIO")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "El nombre del servicio no puede estar vacío")
    @Column(name = "NOMBRE_SERVICIO", nullable = false, length = 255)
    private String serviceName;

    @Positive(message = "El costo debe ser un valor positivo")
    @Column(name = "COSTO", nullable = false)
    private int cost;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(name = "DESCRIPCION", nullable = false, length = 1000)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_FACTURA", nullable = false)
    private Factura factura;

    // Constructor vacío (requerido por JPA)
    public Servicio() {}

    // Constructor con parámetros
    public Servicio(String serviceName, int cost, String descripcion, Factura factura) {
        this.serviceName = serviceName;
        this.cost = cost;
        this.descripcion = descripcion;
        this.factura = factura;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreServicio() {
        return serviceName;
    }

    public void setNombreServicio(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getCosto() {
        return cost;
    }

    public void setCosto(int cost) {
        this.cost = cost;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}