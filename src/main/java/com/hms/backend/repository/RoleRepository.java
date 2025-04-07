package com.hms.backend.repository;

import com.hms.backend.model.Role;
import com.hms.backend.model.StaffRole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for accessing Role entities in the MongoDB database.
 * It extends MongoRepository, providing CRUD operations for Role objects.
 */
public interface RoleRepository extends MongoRepository<Role, String> {

    /**
     * Find a Role by its name.
     *
     * @param name The name of the role represented as an EmployeeRole enum.
     * @return An Optional containing the Role if found, or empty if not found.
     */
    Optional<Role> findByName(StaffRole name);
}
