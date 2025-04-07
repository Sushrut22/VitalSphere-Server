package com.hms.backend.controller;

import com.hms.backend.model.Appointment;
import com.hms.backend.model.Patient;
import com.hms.backend.payload.request.CreatePrescriptionRequest;
import com.hms.backend.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/doctor")
@PreAuthorize("hasRole('DOCTOR')")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    // Patient Management APIs

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> viewPatient(@PathVariable String id) {
        return doctorService.viewPatient(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> viewPatientAppointments() {
        return ResponseEntity.ok(doctorService.viewPatientAppointments());
    }

    @PutMapping("/appointments/{id}/status")
    public ResponseEntity<?> updateAppointmentStatus(@PathVariable String id, @RequestParam String status) {
        try {
            doctorService.updateAppointmentStatus(id, status);
            return ResponseEntity.ok("Appointment status updated successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Prescription Management API

    @PostMapping("/prescriptions")
    public ResponseEntity<?> createPrescription(@Valid @RequestBody CreatePrescriptionRequest createPrescriptionRequest) {
        doctorService.createPrescription(createPrescriptionRequest);
        return new ResponseEntity<>("Prescription created successfully!", HttpStatus.CREATED);
    }
}
