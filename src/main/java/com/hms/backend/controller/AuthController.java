package com.hms.backend.controller;

import com.hms.backend.model.*;
import com.hms.backend.payload.request.LoginRequest;
import com.hms.backend.payload.request.SignupRequest;
import com.hms.backend.payload.response.JwtResponse;
import com.hms.backend.payload.response.MessageResponse;
import com.hms.backend.repository.DoctorRepository;
import com.hms.backend.repository.ReceptionistRepository;
import com.hms.backend.repository.RoleRepository;
import com.hms.backend.repository.UserRepository;
import com.hms.backend.security.jwt.JwtUtils;
import com.hms.backend.security.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600) // Allow cross-origin requests for all origins
@RestController // Indicate that this class is a REST controller
@RequestMapping("/api/auth") // Base URL for authentication-related endpoints
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager; // Handles user authentication

    @Autowired
    UserRepository userRepository; // Repository for user-related database operations

    @Autowired
    RoleRepository roleRepository; // Repository for role-related database operations

    @Autowired
    DoctorRepository doctorRepository; // Inject DoctorRepository NEW CODE

    @Autowired
    ReceptionistRepository receptionistRepository; // Inject ReceptionistRepository NEW CODE 3


    @Autowired
    PasswordEncoder encoder; // Encoder for password hashing

    @Autowired
    JwtUtils jwtUtils; // Utility for generating JWT tokens

    /**
     * Authenticate user and return a JWT token if successful.
     *
     * @param loginRequest The login request containing username and password.
     * @return A ResponseEntity containing the JWT response or an error message.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        // Authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword()));

        // Set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token based on the authentication
        String jwt = jwtUtils.generateJwtToken(authentication);

        // Get user details from the authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Extract user roles into a list
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        // Return a response containing the JWT and user details
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    /**
     * Register a new user account.
     *
     * @param signUpRequest The signup request containing user details.
     * @return A ResponseEntity indicating success or error message.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        // Check if the username is already taken
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check if the email is already in use
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create a new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())); // Encode the password

        Set<String> strRoles = signUpRequest.getRoles(); // Get the roles from the request
        Set<Role> roles = new HashSet<>(); // Initialize a set to hold the user roles

        // Assign roles based on the request or default to user role
        if (strRoles == null) {
            Role adminRole = roleRepository.findByName(StaffRole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "doctor":
                        Role docRole = roleRepository.findByName(StaffRole.ROLE_DOCTOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(docRole);

                        // Create a corresponding Doctor record with name and specialization
                        Doctor doctor = new Doctor(
                                signUpRequest.getDoctorName(),
                                signUpRequest.getDoctorSpecialization(),
                                signUpRequest.getDoctorContactNumber(),
                                signUpRequest.getDoctorAvailability()
                        );
                        Doctor savedDoctor = doctorRepository.save(doctor);
                        user.setDoctorId(savedDoctor.getId());
                        break;
                    case "receptionist":
                        Role recepRole = roleRepository.findByName(StaffRole.ROLE_RECEPTIONIST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(recepRole);
                        // Create a corresponding Receptionist record
                        Receptionist receptionist = new Receptionist(
                                signUpRequest.getReceptionistName(),
                                signUpRequest.getReceptionistContactNumber(),
                                signUpRequest.getReceptionistAvailability()
                        );
                        Receptionist savedReceptionist = receptionistRepository.save(receptionist);
                        user.setReceptionistId(savedReceptionist.getId());
                        break;
                    default:
                        Role adminRole = roleRepository.findByName(StaffRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                }
            });
        }

        // Assign roles to the user and save it to the database
        user.setRoles(roles);
        userRepository.save(user);

        // Return a success message upon successful registration
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
