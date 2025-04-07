# VitalSphere-Server

## Overview/Summary

This is the backend server Repository for **VitalSphere**, a comprehensive Clinic/Hospital Management System (HMS) designed to streamline hospital operations, enhance patient care, and improve communication between staff. This server provides a robust and secure API for managing various aspects of a hospital, including user authentication, patient records, appointments, doctor schedules, billing, and prescriptions.

This backend is built using Spring Boot and leverages MongoDB for data persistence, ensuring scalability and flexibility. It implements JWT (JSON Web Tokens) for secure authentication and authorization, allowing different roles (Admin, Doctor, Receptionist) to access specific functionalities.

This documentation is intended to help developers understand the API endpoints and integrate their own client applications (web, mobile, etc.) with the VitalSphere backend.

## API Documentation

All API endpoints are prefixed with `/api`. The server listens on port `8080` by default.

### 1. Authentication (`/api/auth`)

* **`POST /api/auth/signin`**: Authenticates a user and returns a JWT token upon successful login.
    * **Request Body (JSON):**
        ```json
        {
            "username": "string",
            "password": "string"
        }
        ```
    * **Response (JSON - 200 OK):**
        ```json
        {
            "token": "string",
            "type": "Bearer",
            "id": "string",
            "username": "string",
            "email": "string",
            "roles": ["ROLE_ADMIN" or "ROLE_DOCTOR" or "ROLE_RECEPTIONIST"]
        }
        ```
    * **Response (401 Unauthorized):** Invalid credentials.

* **`POST /api/auth/signup`**: Registers a new user.
    * **Request Body (JSON):**
        ```json
        {
            "username": "string",
            "email": "string",
            "password": "string",
            "roles": ["admin" or "doctor" or "receptionist"],
            "doctorName": "string" (if role is doctor),
            "doctorSpecialization": "string" (if role is doctor),
            "doctorContactNumber": "string" (if role is doctor),
            "doctorAvailability": "string" (if role is doctor),
            "receptionistName": "string" (if role is receptionist),
            "receptionistContactNumber": "string" (if role is receptionist),
            "receptionistAvailability": "string" (if role is receptionist)
        }
        ```
    * **Response (JSON - 200 OK):**
        ```json
        {
            "message": "User registered successfully!"
        }
        ```
    * **Response (400 Bad Request):** Username or email is already taken.

### 2. Admin API (`/api/admin`) - Requires `ROLE_ADMIN`

* **Doctor Management (`/api/admin/doctors`)**:
    * **`POST /api/admin/doctors`**: Adds a new doctor **(Note: This API is Marked for Removal as Authentication API handles new Doctor additions)**.
        * **Request Body (JSON):**
            ```json
            {
                "name": "string",
                "specialization": "string",
                "contactNumber": "string",
                "availability": "string"
            }
            ```
        * **Response (201 Created):** "Doctor added successfully!"
    * **`GET /api/admin/doctors`**: Retrieves all doctors.
        * **Response (JSON - 200 OK):** Array of doctor objects.
    * **`PUT /api/admin/doctors/{doctorId}`**: Updates an existing doctor.
        * **Path Variable:** `{doctorId}` (ID of the doctor to update).
        * **Request Body (JSON):** (Same as POST)
        * **Response (200 OK):** "Doctor updated successfully!"
        * **Response (404 Not Found):** Doctor not found.
    * **`DELETE /api/admin/doctors/{doctorId}`**: Deletes a doctor and their associated user account.
        * **Path Variable:** `{doctorId}` (ID of the doctor to delete).
        * **Response (200 OK):** "Doctor deleted successfully!"
        * **Response (404 Not Found):** Doctor not found.

* **Receptionist Management (`/api/admin/receptionists`)**:
    * **`POST /api/admin/receptionists`**: Adds a new receptionist **(Note: This API is Marked for Removal as Authentication API handles new Receptionist additions)**.
        * **Request Body (JSON):**
            ```json
            {
                "name": "string",
                "contactNumber": "string",
                "availability": "string"
            }
            ```
        * **Response (201 Created):** "Receptionist added successfully!"
    * **`GET /api/admin/receptionists`**: Retrieves all receptionists.
        * **Response (JSON - 200 OK):** Array of receptionist objects.
    * **`PUT /api/admin/receptionists/{receptionistId}`**: Updates an existing receptionist.
        * **Path Variable:** `{receptionistId}` (ID of the receptionist to update).
        * **Request Body (JSON):** (Same as POST)
        * **Response (200 OK):** "Receptionist updated successfully!"
        * **Response (404 Not Found):** Receptionist not found.
    * **`DELETE /api/admin/receptionists/{receptionistId}`**: Deletes a receptionist and their associated user account.
        * **Path Variable:** `{receptionistId}` (ID of the receptionist to delete).
        * **Response (200 OK):** "Receptionist deleted successfully!"
        * **Response (404 Not Found):** Receptionist not found.

* **Patient Management (`/api/admin/patients`)**:
    * **`GET /api/admin/patients`**: Retrieves all patients.
        * **Response (JSON - 200 OK):** Array of patient objects.
    * **`GET /api/admin/patients/{patientId}`**: Retrieves a specific patient.
        * **Path Variable:** `{patientId}` (ID of the patient).
        * **Response (JSON - 200 OK):** Patient object.
        * **Response (404 Not Found):** Patient not found.

* **Billing Management (`/api/admin/bills`)**:
    * **`GET /api/admin/bills`**: Retrieves all bills.
        * **Response (JSON - 200 OK):** Array of bill objects.

* **Prescription Management (`/api/admin/prescriptions`)**:
    * **`GET /api/admin/prescriptions`**: Retrieves all prescriptions.
        * **Response (JSON - 200 OK):** Array of prescription objects.

### 3. Receptionist API (`/api/receptionist`) - Requires `ROLE_RECEPTIONIST`

* **Patient Management (`/api/receptionist/patients`)**:
    * **`POST /api/receptionist/patients`**: Adds a new patient.
        * **Request Body (JSON):**
            ```json
            {
                "name": "string",
                "age": "integer",
                "gender": "string",
                "contactNumber": "string",
                "medicalHistory": "string"
            }
            ```
        * **Response (201 Created):** "Patient registered successfully!"
    * **`GET /api/receptionist/patients`**: Retrieves all patients.
        * **Response (JSON - 200 OK):** Array of patient objects.
    * **`PUT /api/receptionist/patients/{patientId}`**: Updates an existing patient.
        * **Path Variable:** `{patientId}` (ID of the patient to update).
        * **Request Body (JSON):** (Same as POST)
        * **Response (200 OK):** "Patient updated successfully!"
        * **Response (404 Not Found):** Patient not found.
    * **`DELETE /api/receptionist/patients/{patientId}`**: Deletes a patient.
        * **Path Variable:** `{patientId}` (ID of the patient to delete).
        * **Response (200 OK):** "Patient deleted successfully!"
        * **Response (404 Not Found):** Patient not found.

* **Appointment Management (`/api/receptionist/appointments`)**:
    * **`POST /api/receptionist/appointments`**: Schedules a new patient appointment.
        * **Request Body (JSON):**
            ```json
            {
                "patientId": "string",
                "doctorId": "string",
                "appointmentDateTime": "YYYY-MM-DDTHH:mm:ss",
                "reason": "string"
            }
            ```
        * **Response (201 Created):** "Appointment scheduled successfully!"
        * **Response (404 Not Found):** Patient or Doctor not found.
    * **`GET /api/receptionist/appointments`**: Retrieves all scheduled appointments.
        * **Response (JSON - 200 OK):** Array of appointment objects.
    * **`GET /api/receptionist/appointments/by-date?date=YYYY-MM-DD`**: Retrieves appointments for a specific date (UTC). **(Note: Known Issue in Datewise Appointment retrieval)**.
        * **Query Parameter:** `date` (YYYY-MM-DD format).
        * **Response (JSON - 200 OK):** Array of appointment objects for the given date.

* **Billing Management (`/api/receptionist/bills`)**:
    * **`GET /api/receptionist/bills/{patientId}`**: Retrieves all bills for a specific patient.
        * **Path Variable:** `{patientId}` (ID of the patient).
        * **Response (JSON - 200 OK):** Array of bill objects for the patient.
    * **`GET /api/receptionist/bills`**: Retrieves all bills.
        * **Response (JSON - 200 OK):** Array of all bill objects.
    * **`POST /api/receptionist/bills`**: Creates a new bill for a patient.
        * **Request Body (JSON):**
            ```json
            {
                "patientId": "string",
                "items": ["string", "string", ...],
                "amount": "number"
            }
            ```
        * **Response (201 Created):** "Bill created successfully!"
        * **Response (404 Not Found):** Patient not found.

### 4. Doctor API (`/api/doctor`) - Requires `ROLE_DOCTOR`

* **Patient Management (`/api/doctor/patients`)**:
    * **`GET /api/doctor/patients/{patientId}`**: Retrieves details of a specific patient.
        * **Path Variable:** `{patientId}` (ID of the patient).
        * **Response (JSON - 200 OK):** Patient object.
        * **Response (404 Not Found):** Patient not found.

* **Appointment Management (`/api/doctor/appointments`)**:
    * **`GET /api/doctor/appointments`**: Retrieves all appointments scheduled for the logged-in doctor.
        * **Response (JSON - 200 OK):** Array of appointment objects.
    * **`PUT /api/doctor/appointments/{appointmentId}/status?status=ACCEPTED|REJECTED`**: Updates the status of an appointment.
        * **Path Variable:** `{appointmentId}` (ID of the appointment).
        * **Query Parameter:** `status` (either `ACCEPTED` or `REJECTED`).
        * **Response (200 OK):** "Appointment status updated successfully!"
        * **Response (400 Bad Request):** Invalid status provided.
        * **Response (404 Not Found):** Appointment not found.

* **Prescription Management (`/api/doctor/prescriptions`)**:
    * **`POST /api/doctor/prescriptions`**: Creates a new prescription for a patient.
        * **Request Body (JSON):**
            ```json
            {
                "patientId": "string",
                "medications": ["string", "string", ...],
                "dosage": "string",
                "instructions": "string"
            }
            ```
        * **Response (201 Created):** "Prescription created successfully!"
        * **Response (404 Not Found):** Patient not found.

## Tech Stack

* **Backend Framework:** Spring Boot 3.x
* **Database:** MongoDB
* **Authentication:** JWT, Spring Security 6.x
* **Build Tool:** Maven
* **Language:** Java 17


## Getting Started

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/Sushrut22/VitalSphere-Server.git
    ```
2.  **Navigate to the project directory:**
    ```bash
    cd VitalSphere-Server
    ```
3.  **Configure MongoDB:** Ensure you have MongoDB installed and running. Update the `spring.data.mongodb.uri` in `src/main/resources/application.properties` with your MongoDB connection details.
4.  **Build the project:**
    ```bash
    mvn clean install
    ```
5.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
    The server will start at `http://localhost:8080`.
6.  **Explore the API:** Use tools like Postman, Insomnia, or `curl` to interact with the API endpoints. Remember to obtain a JWT token by logging in to access protected routes (Admin, Doctor, Receptionist).

## Notes

* All protected API endpoints require a valid JWT token to be included in the `Authorization` header as a Bearer token (e.g., `Authorization: Bearer <your_jwt_token>`). Jwt token can be found at Signin Response.
* Refer to the API documentation for request bodies, response formats, and required roles for each endpoint.
* The `application.properties` file contains sensitive information and should not be committed to version control. A sample `application.properties.example` is provided for configuration guidance.

---
