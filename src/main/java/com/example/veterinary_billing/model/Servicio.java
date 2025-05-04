package com.example.veterinary_billing.model;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "SERVICIO")
public class Servicio extends RepresentationModel<Servicio> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "El nombre del servicio no puede estar vacío")
    @Size(max = 255, message = "El nombre del servicio no puede tener más de 255 caracteres")
    @Column(name = "NOMBRE_SERVICIO", nullable = false, length = 255)
    private String nombreServicio;

    @Positive(message = "El costo debe ser un valor positivo")
    @Column(name = "COSTO", nullable = false)
    private int costo;

    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 1000, message = "La descripción no puede tener más de 1000 caracteres")
    @Column(name = "DESCRIPCION", nullable = false, length = 1000)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "ID_FACTURA", nullable = false)
    @NotNull(message = "La factura no puede ser nula")
    private Factura factura;

    // Constructor vacío (requerido por JPA)
    public Servicio() {}

    // Constructor con parámetros
    public Servicio(String nombreServicio, int costo, String descripcion, Factura factura) {
        this.nombreServicio = nombreServicio;
        this.costo = costo;
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
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
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