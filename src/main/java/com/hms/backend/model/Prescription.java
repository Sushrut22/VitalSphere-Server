package com.hms.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "prescriptions")
public class Prescription {
    @Id
    private String id;
    @DBRef
    private Patient patient;
    @DBRef
    private Doctor doctor;
    private LocalDateTime prescriptionDateTime;
    private List<String> medications;
    private String dosage;
    private String instructions;

    public Prescription() {
    }

    public Prescription(Patient patient, Doctor doctor, List<String> medications, String dosage, String instructions) {
        this.patient = patient;
        this.doctor = doctor;
        this.prescriptionDateTime = LocalDateTime.now();
        this.medications = medications;
        this.dosage = dosage;
        this.instructions = instructions;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getPrescriptionDateTime() {
        return prescriptionDateTime;
    }

    public void setPrescriptionDateTime(LocalDateTime prescriptionDateTime) {
        this.prescriptionDateTime = prescriptionDateTime;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
