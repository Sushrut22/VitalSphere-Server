package com.hms.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class AddDoctorRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String specialization;
    @NotBlank
    private String contactNumber;
    private String availability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }
}
