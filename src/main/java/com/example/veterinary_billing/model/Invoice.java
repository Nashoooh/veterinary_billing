package com.example.veterinary_billing.model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private int id;
    private List<ServiceProvided> services;
    private String paymentMethod;
    private double totalAmount;
    private boolean paid;

    public Invoice(int id) {
        this.id = id;
        this.services = new ArrayList<>();
        this.totalAmount = 0.0;
        this.paid = false;
        this.paymentMethod = "Cash";
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public List<ServiceProvided> getServices() {
        return services;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void markAsPaid() {
        this.paid = true;
    }

    public void addService(ServiceProvided service) {
        services.add(service);
        totalAmount += service.getCost();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}