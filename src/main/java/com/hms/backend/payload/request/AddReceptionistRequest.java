package com.hms.backend.payload.request;

import jakarta.validation.constraints.NotBlank;

public class AddReceptionistRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String contactNumber;
    private String availability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
