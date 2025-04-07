package com.hms.backend.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    // Doctor specific fields
    private String doctorName;
    private String doctorSpecialization;
    private String doctorContactNumber; // Added
    private String doctorAvailability;   // Added

    // Receptionist specific fields
    private String receptionistName;
    private String receptionistContactNumber;
    private String receptionistAvailability; // Added

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRole(Set<String> roles) {
        this.roles = roles;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDoctorContactNumber() {
        return doctorContactNumber;
    }

    public void setDoctorContactNumber(String doctorContactNumber) {
        this.doctorContactNumber = doctorContactNumber;
    }

    public String getDoctorAvailability() {
        return doctorAvailability;
    }

    public void setDoctorAvailability(String doctorAvailability) {
        this.doctorAvailability = doctorAvailability;
    }

    public String getReceptionistName() {
        return receptionistName;
    }

    public void setReceptionistName(String receptionistName) {
        this.receptionistName = receptionistName;
    }

    public String getReceptionistContactNumber() {
        return receptionistContactNumber;
    }

    public void setReceptionistContactNumber(String receptionistContactNumber) {
        this.receptionistContactNumber = receptionistContactNumber;
    }

    public String getReceptionistAvailability() {
        return receptionistAvailability;
    }

    public void setReceptionistAvailability(String receptionistAvailability) {
        this.receptionistAvailability = receptionistAvailability;
    }
}

