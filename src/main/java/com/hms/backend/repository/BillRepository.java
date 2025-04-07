package com.hms.backend.repository;

import com.hms.backend.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
    List<Bill> findByPatientId(String patientId);
}