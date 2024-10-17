# Hospital Management System

This project is a simple **Hospital Management System** developed in Java using JDBC for database management. The system allows for managing patients, doctors, and appointments efficiently.

## Features
- **Add Patients**: Register new patients in the system.
- **View Patients**: Display a list of all registered patients.
- **View Doctors**: Display a list of all registered doctors.
- **Book Appointments**: Patients can book an appointment with available doctors.
- **Doctor Login**: Doctors can log in using their ID to view their appointed patients.

## Prerequisites
1. **Java JDK 8 or above**: Make sure Java is installed on your system.
2. **MySQL**: The system uses a MySQL database to store information about patients, doctors, and appointments.
3. **MySQL Connector/J**: Add the MySQL JDBC driver to your project dependencies.
   - [Download MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

## Database Setup
Run the following SQL script to set up the database schema required for the project:

```sql
CREATE DATABASE Hospital;
USE Hospital;

CREATE TABLE Patients (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Age INT NOT NULL,
    Gender VARCHAR(10) NOT NULL
);

CREATE TABLE Doctors (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(255) NOT NULL,
    Specialization VARCHAR(255) NOT NULL
);

CREATE TABLE Appointment (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES Patients(Id),
    FOREIGN KEY (doctor_id) REFERENCES Doctors(Id)
);

INSERT INTO Doctors(Name, Specialization) VALUES
('Pankaj Jain', 'ENT'),
('Harshit Anand', 'NeuroSurgeon'),
('Nikhil Shukal', 'Pathologist'),
('Aditya Yadav', 'Dermatologist'),
('Abhaya Pratap', 'Physiotherapist'),
('Aditya Mishra', 'Sexual Health Specialist'),
('Rishabh Rai', 'Gynecologist'),
('Adarsh Singh', 'Pediatrician'),
('Keshu Gupta', 'Psychologist');


