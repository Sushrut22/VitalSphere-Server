package com.hms.backend.repository;

import com.hms.backend.model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends MongoRepository<Appointment, String> {
    List<Appointment> findByAppointmentDateTimeBetween(LocalDate startOfDay, LocalDate endOfDay);
    List<Appointment> findByDoctorId(String doctorId);
}