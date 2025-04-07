package com.hms.backend.service;

import com.hms.backend.model.Appointment;
import com.hms.backend.model.Doctor;
import com.hms.backend.model.Patient;
import com.hms.backend.model.Prescription;
import com.hms.backend.payload.request.CreatePrescriptionRequest;
import com.hms.backend.repository.AppointmentRepository;
import com.hms.backend.repository.DoctorRepository;
import com.hms.backend.repository.PatientRepository;
import com.hms.backend.repository.PrescriptionRepository;
import com.hms.backend.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    DoctorRepository doctorRepository;

    public Optional<Patient> viewPatient(String id) {
        return patientRepository.findById(id);
    }

//    public List<Appointment> viewPatientAppointments() {
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<Doctor> doctor = doctorRepository.findByName(userDetails.getUsername()); // Assuming username is name for Doctor
//        return doctor.map(value -> appointmentRepository.findByDoctorId(value.getId())).orElse(List.of());
//    }
    // NEW CODE
    public List<Appointment> viewPatientAppointments() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        String doctorId = userDetailsImpl.getDoctorId();
        if (doctorId != null) {
            return appointmentRepository.findByDoctorId(doctorId);
        }
        return List.of();
    }

    public void updateAppointmentStatus(String id, String status) {
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        appointmentOptional.ifPresent(appointment -> {
            if (status.equalsIgnoreCase("ACCEPTED") || status.equalsIgnoreCase("REJECTED")) {
                appointment.setStatus(status.toUpperCase());
                appointmentRepository.save(appointment);
            } else {
                throw new IllegalArgumentException("Invalid appointment status. Must be ACCEPTED or REJECTED.");
            }
        });
    }

//    public void createPrescription(CreatePrescriptionRequest request) {
//        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
//        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Optional<Doctor> doctorOptional = doctorRepository.findByName(userDetails.getUsername()); // Assuming username is name for Doctor
//
//        if (patientOptional.isPresent() && doctorOptional.isPresent()) {
//            Prescription prescription = new Prescription(
//                    patientOptional.get(),
//                    doctorOptional.get(),
//                    request.getMedications(),
//                    request.getDosage(),
//                    request.getInstructions()
//            );
//            prescriptionRepository.save(prescription);
//        }
//    }
    // NEW CODE
    public void createPrescription(CreatePrescriptionRequest request) {
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) userDetails;
        String doctorId = userDetailsImpl.getDoctorId();

        if (patientOptional.isPresent() && doctorId != null) {
            Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);
            doctorOptional.ifPresent(doctor -> {
                Prescription prescription = new Prescription(
                        patientOptional.get(),
                        doctor,
                        request.getMedications(),
                        request.getDosage(),
                        request.getInstructions()
                );
                prescriptionRepository.save(prescription);
            });
        }
    }
}
