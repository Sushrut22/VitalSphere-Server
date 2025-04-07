package com.hms.backend.repository;

import com.hms.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * Repository interface for accessing User entities in the MongoDB database.
 * It extends MongoRepository, providing CRUD operations for User objects.
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a User by their username.
     *
     * @param username The username of the user.
     * @return An Optional containing the User if found, or empty if not found.
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a username already exists in the database.
     *
     * @param username The username to check.
     * @return A Boolean indicating whether the username exists (true) or not (false).
     */
    Boolean existsByUsername(String username);

    /**
     * Check if an email already exists in the database.
     *
     * @param email The email to check.
     * @return A Boolean indicating whether the email exists (true) or not (false).
     */
    Boolean existsByEmail(String email);

    Optional<Object> findByDoctorId(String doctorId);
    Optional<User> findByReceptionistId(String receptionistId); // Add this method

}

