package com.hms.backend.repository;

import com.hms.backend.model.Receptionist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptionistRepository extends MongoRepository<Receptionist, String> {
}