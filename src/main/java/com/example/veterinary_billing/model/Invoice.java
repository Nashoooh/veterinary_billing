package com.example.veterinary_billing.model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private String id;
    private List<ServiceProvided> services;
    private String paymentMethod;
    private int totalAmount;

    public Invoice(String id, String paymentMethod) {
        this.id = id;
        this.services = new ArrayList<>();
        this.totalAmount = 0;
        this.paymentMethod = paymentMethod;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    // Este metodo permite obtener la lista de servicios asociados a la factura
    public List<ServiceProvided> getServiceProvideds() {
        return services;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Este metodo permite agregar un servicio a la factura y que actualice el total
    public void addService(ServiceProvided service) {
        services.add(service); // Agrego el servicio a la lista de asociado a la factura
        totalAmount += service.getCost(); // Actualizo el total de la factura
    }

}