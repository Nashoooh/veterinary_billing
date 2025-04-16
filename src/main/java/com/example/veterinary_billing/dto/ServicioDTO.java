package com.example.veterinary_billing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServicioDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nombre_servicio")
    private String nombreServicio;

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("costo")
    private int costo;

    // Constructor
    public ServicioDTO(Long id, String nombreServicio, String descripcion, int costo) {
        this.id = id;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.costo = costo;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }
}