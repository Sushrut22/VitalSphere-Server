package com.hms.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "receptionists")
public class Receptionist {
    @Id
    private String id;
    private String name;
    private String contactNumber;
    private String availability;

    public Receptionist() {
    }

    public Receptionist(String name, String contactNumber, String availability) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.availability = availability;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
