package com.example.veterinary_billing.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ServiceProvided {
    @NotBlank(message = "El nombre del servicio no puede estar vacío") // Utilizo estos decoradores para evitar que este vacio el campo
    private String serviceName;

    @Positive(message = "El costo debe ser un valor positivo") // Utilizo estos decoradores para evitar que el costo sea negativo
    private int cost;

    public ServiceProvided(String serviceName, int cost) {
        this.serviceName = serviceName;
        this.cost = cost;        
    }
    
    // Getters y Setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
