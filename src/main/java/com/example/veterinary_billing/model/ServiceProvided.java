package com.example.veterinary_billing.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ServiceProvided {
    @NotBlank(message = "El nombre del servicio no puede estar vacío")
    private String serviceName;

    @Positive(message = "El costo debe ser un valor positivo")
    private double cost;

    // Getters y Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
