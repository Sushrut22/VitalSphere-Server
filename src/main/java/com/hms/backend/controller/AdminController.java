package com.hms.backend.controller;

import com.hms.backend.model.*;
import com.hms.backend.payload.request.AddDoctorRequest;
import com.hms.backend.payload.request.AddReceptionistRequest;
import com.hms.backend.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    AdminService adminService;

    // Doctor Management APIs

    @PostMapping("/doctors")
    public ResponseEntity<?> addDoctor(@Valid @RequestBody AddDoctorRequest addDoctorRequest) {
        adminService.addDoctor(addDoctorRequest);
        return new ResponseEntity<>("Doctor added successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> viewAllDoctors() {
        return ResponseEntity.ok(adminService.viewAllDoctors());
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> editDoctor(@PathVariable String id, @Valid @RequestBody AddDoctorRequest updateDoctorRequest) {
        adminService.editDoctor(id, updateDoctorRequest);
        return ResponseEntity.ok("Doctor updated successfully!");
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable String id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.ok("Doctor deleted successfully!");
    }

    // Receptionist Management APIs

    @PostMapping("/receptionists")
    public ResponseEntity<?> addReceptionist(@Valid @RequestBody AddReceptionistRequest addReceptionistRequest) {
        adminService.addReceptionist(addReceptionistRequest);
        return new ResponseEntity<>("Receptionist added successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/receptionists")
    public ResponseEntity<List<Receptionist>> viewAllReceptionists() {
        return ResponseEntity.ok(adminService.viewAllReceptionists());
    }

    @PutMapping("/receptionists/{id}")
    public ResponseEntity<?> editReceptionist(@PathVariable String id, @Valid @RequestBody AddReceptionistRequest updateReceptionistRequest) {
        adminService.editReceptionist(id, updateReceptionistRequest);
        return ResponseEntity.ok("Receptionist updated successfully!");
    }

    @DeleteMapping("/receptionists/{id}")
    public ResponseEntity<?> deleteReceptionist(@PathVariable String id) {
        adminService.deleteReceptionist(id);
        return ResponseEntity.ok("Receptionist deleted successfully!");
    }

    // Patient Management APIs

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> viewAllPatients() {
        return ResponseEntity.ok(adminService.viewAllPatients());
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> viewPatient(@PathVariable String id) {
        return adminService.viewPatient(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Billing Management API

    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> viewAllBills() {
        return ResponseEntity.ok(adminService.viewAllBills());
    }

    // Prescription Management API

    @GetMapping("/prescriptions")
    public ResponseEntity<List<Prescription>> viewAllPrescriptions() {
        return ResponseEntity.ok(adminService.viewAllPrescriptions());
    }
}
