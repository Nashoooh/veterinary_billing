package com.example.veterinary_billing.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FACTURA")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "METODO_PAGO", nullable = false, length = 255)
    private String metodoPago;

    @Column(name = "MONTO_TOTAL", nullable = false)
    private int montoTotal;

    @Column(name = "FECHA", nullable = false)
    private LocalDate fecha;  
    
    @Column(name = "PAGADO", nullable = false)
    private boolean pagado;

    public Factura(){}

    public Factura(Long id, String metodoPago, int paymentMethod, int totalAmount, LocalDate fecha, boolean pagado) {
        this.id = id;
        this.metodoPago = metodoPago;
        this.montoTotal = paymentMethod;
        this.fecha = LocalDate.now(); // Fecha actual
        this.pagado = false; // Por defecto, la factura no está pagada
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

}