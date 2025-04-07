package com.hms.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "bills")
public class Bill {
    @Id
    private String id;
    @DBRef
    private Patient patient;
    private LocalDateTime billingDateTime;
    private List<String> items; // Description of services/treatments
    private double amount;
    private String paymentStatus; // e.g., "Pending", "Paid"

    public Bill() {
    }

    public Bill(Patient patient, List<String> items, double amount) {
        this.patient = patient;
        this.billingDateTime = LocalDateTime.now();
        this.items = items;
        this.amount = amount;
        this.paymentStatus = "Pending";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public LocalDateTime getBillingDateTime() {
        return billingDateTime;
    }

    public void setBillingDateTime(LocalDateTime billingDateTime) {
        this.billingDateTime = billingDateTime;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
