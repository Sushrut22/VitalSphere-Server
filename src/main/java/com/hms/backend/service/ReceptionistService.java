package com.hms.backend.service;

import com.hms.backend.model.Appointment;
import com.hms.backend.model.Bill;
import com.hms.backend.model.Doctor;
import com.hms.backend.model.Patient;
import com.hms.backend.payload.request.AddPatientAppointmentRequest;
import com.hms.backend.payload.request.AddPatientRequest;
import com.hms.backend.payload.request.CreateBillRequest;
import com.hms.backend.repository.AppointmentRepository;
import com.hms.backend.repository.BillRepository;
import com.hms.backend.repository.DoctorRepository;
import com.hms.backend.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReceptionistService {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    BillRepository billRepository;

    // Patient Management

    public void addPatient(AddPatientRequest request) {
        Patient patient = new Patient(request.getName(), request.getAge(), request.getGender(), request.getContactNumber(), request.getMedicalHistory());
        patientRepository.save(patient);
    }

    public List<Patient> viewAllPatients() {
        return patientRepository.findAll();
    }

    public void editPatient(String id, AddPatientRequest request) {
        Optional<Patient> patientOptional = patientRepository.findById(id);
        patientOptional.ifPresent(patient -> {
            patient.setName(request.getName());
            patient.setAge(request.getAge());
            patient.setGender(request.getGender());
            patient.setContactNumber(request.getContactNumber());
            patient.setMedicalHistory(request.getMedicalHistory());
            patientRepository.save(patient);
        });
    }

    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

    // Appointment Management

    public void addPatientAppointment(AddPatientAppointmentRequest request) {
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        Optional<Doctor> doctorOptional = doctorRepository.findById(request.getDoctorId());

        if (patientOptional.isPresent() && doctorOptional.isPresent()) {
            Appointment appointment = new Appointment(
                    patientOptional.get(),
                    doctorOptional.get(),
                    request.getAppointmentDateTime(),
                    request.getReason()
            );
            appointmentRepository.save(appointment);
        }
    }

    public List<Appointment> viewAllPatientAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> searchPatientAppointmentByDate(LocalDate date) {
        LocalDate startOfDay = LocalDate.from(date.atStartOfDay());
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        return appointmentRepository.findByAppointmentDateTimeBetween(startOfDay, LocalDate.from(endOfDay));
    }

    // Billing Management

    public List<Bill> viewBill(String patientId) {
        return billRepository.findByPatientId(patientId);
    }

    public List<Bill> viewAllBills() {
        return billRepository.findAll();
    }

    public void createBill(CreateBillRequest request) {
        Optional<Patient> patientOptional = patientRepository.findById(request.getPatientId());
        if (patientOptional.isPresent()) {
            Bill bill = new Bill(patientOptional.get(), request.getItems(), request.getAmount());
            billRepository.save(bill);
        }
    }
}