package com.hms.backend.service;

import com.hms.backend.model.*;
import com.hms.backend.payload.request.AddDoctorRequest;
import com.hms.backend.payload.request.AddReceptionistRequest;
import com.hms.backend.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    ReceptionistRepository receptionistRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    PrescriptionRepository prescriptionRepository;

    @Autowired
    UserRepository userRepository; // Inject UserRepository

    // Doctor Management

    public void addDoctor(AddDoctorRequest request) {
        Doctor doctor = new Doctor(request.getName(), request.getSpecialization(), request.getContactNumber(), request.getAvailability());
        doctorRepository.save(doctor);
    }

    public List<Doctor> viewAllDoctors() {
        return doctorRepository.findAll();
    }

    public void editDoctor(String id, AddDoctorRequest request) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        doctorOptional.ifPresent(doctor -> {
            doctor.setName(request.getName());
            doctor.setSpecialization(request.getSpecialization());
            doctor.setContactNumber(request.getContactNumber());
            doctor.setAvailability(request.getAvailability());
            doctorRepository.save(doctor);
        });
    }

//    public void deleteDoctor(String id) {
//        doctorRepository.deleteById(id);
//    }
    public void deleteDoctor(String id) {
        Optional<Doctor> doctorOptional = doctorRepository.findById(id);
        doctorOptional.ifPresent(doctor -> {
            // Find and delete the associated user
            userRepository.findByDoctorId(doctor.getId()).ifPresent(user -> {
                userRepository.delete((User) user);
                System.out.println("Associated user deleted: " + ((User) user).getUsername());
            });
            // Delete the doctor
            doctorRepository.deleteById(id);
            System.out.println("Doctor deleted with ID: " + id);
        });
    }

    // Receptionist Management

    public void addReceptionist(AddReceptionistRequest request) {
        Receptionist receptionist = new Receptionist(request.getName(), request.getContactNumber(), request.getAvailability());
        receptionistRepository.save(receptionist);
    }

    public List<Receptionist> viewAllReceptionists() {
        return receptionistRepository.findAll();
    }

    public void editReceptionist(String id, AddReceptionistRequest request) {
        Optional<Receptionist> receptionistOptional = receptionistRepository.findById(id);
        receptionistOptional.ifPresent(receptionist -> {
            receptionist.setName(request.getName());
            receptionist.setContactNumber(request.getContactNumber());
            receptionist.setAvailability(request.getAvailability());
            receptionistRepository.save(receptionist);
        });
    }

//    public void deleteReceptionist(String id) {
//        receptionistRepository.deleteById(id);
//    }
    public void deleteReceptionist(String id) {
        Optional<Receptionist> receptionistOptional = receptionistRepository.findById(id);
        receptionistOptional.ifPresent(receptionist -> {
            // Find and delete the associated user
            userRepository.findByReceptionistId(receptionist.getId()).ifPresent(user -> {
                userRepository.delete(user);
                System.out.println("Associated user deleted: " + user.getUsername());
            });
            // Delete the receptionist
            receptionistRepository.deleteById(id);
            System.out.println("Receptionist deleted with ID: " + id);
        });
    }

    // Patient Management

    public List<Patient> viewAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> viewPatient(String id) {
        return patientRepository.findById(id);
    }

    // Billing Management

    public List<Bill> viewAllBills() {
        return billRepository.findAll();
    }

    // Prescription Management

    public List<Prescription> viewAllPrescriptions() {
        return prescriptionRepository.findAll();
    }
}