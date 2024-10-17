package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/HospitalDb";
    private static final String username = "root";
    private static final String password = "pass123";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            Patient patient = new Patient(connection, sc);
            Doctor doctor = new Doctor(connection, sc);

            while (true) {
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Doctor Login");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Add Patient
                        patient.addPatient();
                        break;
                    case 2:
                        // View Patients
                        patient.viewPatients();
                        break;
                    case 3:
                        // View Doctors
                        doctor.viewDoctors();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient, doctor, connection, sc);
                        break;
                    case 5:
                        // Doctor Login
                        if (doctor.login()) {
                            System.out.print("Enter your Doctor ID to view your patients: ");
                            int doctorId = sc.nextInt();
                            doctor.viewMyPatients(doctorId);
                        }
                        break;
                    case 6:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM!!");
                        return;
                    default:
                        System.out.println("Enter a valid choice!!!");
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sc.close();
    }

    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner sc) {
        System.out.print("Enter Patient Id: ");
        int patientId = sc.nextInt();
        System.out.print("Enter Doctor Id: ");
        int doctorId = sc.nextInt();
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String appointment = sc.next();

        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {
            if (checkDoctorAvailability(doctorId, appointment, connection)) {
                String query = "INSERT INTO Appointment(patient_id, doctor_id, appointment_date) VALUES(?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, patientId);
                    preparedStatement.setInt(2, doctorId);
                    preparedStatement.setString(3, appointment);
                    int affectedRow = preparedStatement.executeUpdate();
                    if (affectedRow > 0) {
                        System.out.println("Appointment Booked!");
                    } else {
                        System.out.println("Failed to Book Appointment!");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date!!");
            }
        } else {
            System.out.println("Either doctor or patient doesn't exist!!!");
        }
    }

    public static boolean checkDoctorAvailability(int doctorId, String appointment, Connection connection) {
        String query = "SELECT COUNT(*) FROM Appointment WHERE doctor_id = ? AND appointment_date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setString(2, appointment);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count < 10; // Limit to 10 appointments
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
