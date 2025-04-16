package com.example.veterinary_billing.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FacturaConServiciosDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("metodo_pago")
    private String metodoPago;

    @JsonProperty("monto_total")
    private int montoTotal;

    @JsonProperty("fecha")
    private LocalDate fecha;

    @JsonProperty("pagado")
    private boolean pagado;

    @JsonProperty("servicios")
    private List<ServicioDTO> servicios;

    // Constructor
    public FacturaConServiciosDTO(Long id, String metodoPago, int montoTotal, LocalDate fecha, boolean pagado, List<ServicioDTO> servicios) {
        this.id = id;
        this.metodoPago = metodoPago;
        this.montoTotal = montoTotal;
        this.fecha = fecha;
        this.pagado = pagado;
        this.servicios = servicios;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public List<ServicioDTO> getServicios() {
        return servicios;
    }

    public void setServicios(List<ServicioDTO> servicios) {
        this.servicios = servicios;
    }
}