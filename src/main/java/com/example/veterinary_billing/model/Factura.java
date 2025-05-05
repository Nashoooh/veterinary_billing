package com.example.veterinary_billing.model;

import java.time.LocalDate;

import org.springframework.hateoas.RepresentationModel;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "FACTURA")
public class Factura extends RepresentationModel<Factura> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "METODO_PAGO", nullable = false, length = 255)
    @NotBlank(message = "El método de pago no puede estar vacío")
    @Size(max = 255, message = "El método de pago no puede tener más de 255 caracteres")
    private String metodoPago;

    @Column(name = "MONTO_TOTAL", nullable = false)
    @Positive(message = "El monto total debe ser un valor positivo")
    private int montoTotal;

    @Column(name = "FECHA", nullable = false)
    @FutureOrPresent(message = "La fecha no puede ser anterior a la fecha actual")
    private LocalDate fecha;  
    
    @Column(name = "PAGADO", nullable = false)
    private boolean pagado;

    public Factura(){}

    public Factura(Long id, String metodoPago, int totalAmount, LocalDate fecha, boolean pagado) {
        this.id = id;
        this.metodoPago = metodoPago;
        this.montoTotal = totalAmount;
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

    public void setId(Long id) {
        this.id = id;
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