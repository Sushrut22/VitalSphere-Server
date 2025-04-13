package com.hms.backend.controller;

import com.hms.backend.model.Appointment;
import com.hms.backend.model.Bill;
import com.hms.backend.model.Doctor;
import com.hms.backend.model.Patient;
import com.hms.backend.payload.request.AddPatientAppointmentRequest;
import com.hms.backend.payload.request.AddPatientRequest;
import com.hms.backend.payload.request.CreateBillRequest;
import com.hms.backend.service.AdminService;
import com.hms.backend.service.DoctorService;
import com.hms.backend.service.ReceptionistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/receptionist")
@PreAuthorize("hasRole('RECEPTIONIST')")
public class ReceptionistController {

    @Autowired
    ReceptionistService receptionistService;

    @Autowired
    AdminService adminService;

    // Patient Management APIs

    @PostMapping("/patients")
    public ResponseEntity<?> addPatient(@Valid @RequestBody AddPatientRequest addPatientRequest) {
        receptionistService.addPatient(addPatientRequest);
        return new ResponseEntity<>("Patient registered successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> viewAllPatients() {
        return ResponseEntity.ok(receptionistService.viewAllPatients());
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<?> editPatient(@PathVariable String id, @Valid @RequestBody AddPatientRequest updatePatientRequest) {
        receptionistService.editPatient(id, updatePatientRequest);
        return ResponseEntity.ok("Patient updated successfully!");
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable String id) {
        receptionistService.deletePatient(id);
        return ResponseEntity.ok("Patient deleted successfully!");
    }

    // Appointment Management APIs

    @PostMapping("/appointments")
    public ResponseEntity<?> addPatientAppointment(@Valid @RequestBody AddPatientAppointmentRequest addPatientAppointmentRequest) {
        receptionistService.addPatientAppointment(addPatientAppointmentRequest);
        return new ResponseEntity<>("Appointment scheduled successfully!", HttpStatus.CREATED);
    }

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> viewAllPatientAppointments() {
        return ResponseEntity.ok(receptionistService.viewAllPatientAppointments());
    }

    @GetMapping("/appointments/by-date")
    public ResponseEntity<List<Appointment>> searchPatientAppointmentByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(receptionistService.searchPatientAppointmentByDate(date));
    }

    // Billing Management APIs

    @GetMapping("/bills/{patientId}")
    public ResponseEntity<List<Bill>> viewBill(@PathVariable String patientId) {
        return ResponseEntity.ok(receptionistService.viewBill(patientId));
    }

    @GetMapping("/bills")
    public ResponseEntity<List<Bill>> viewAllBills() {
        return ResponseEntity.ok(receptionistService.viewAllBills());
    }

    @PostMapping("/bills")
    public ResponseEntity<?> createBill(@Valid @RequestBody CreateBillRequest createBillRequest) {
        receptionistService.createBill(createBillRequest);
        return new ResponseEntity<>("Bill created successfully!", HttpStatus.CREATED);
    }

    // Using this Receptionist will be able to view all Doctors associated with the Hospital
    @GetMapping("/doctors")
    public ResponseEntity<List<Doctor>> viewAllDoctors() {
        return ResponseEntity.ok(adminService.viewAllDoctors());
    }
}