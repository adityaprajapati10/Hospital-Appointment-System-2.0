package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Doctor {
    private Connection connection;
    private Scanner scanner;

    public Doctor(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public boolean login() {
        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        String query = "SELECT * FROM Doctors WHERE Id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true; // Doctor exists
            } else {
                System.out.println("Invalid Doctor ID. Please try again.");
                return false; // Invalid ID
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewMyPatients(int doctorId) {
        String query = "SELECT p.* FROM Patients p " +
                "JOIN Appointment a ON p.Id = a.patient_id " +
                "WHERE a.doctor_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients for Doctor ID " + doctorId + ":");
            System.out.println("+------------+--------------------+----------+------------+");
            System.out.println("| Patient Id | Name               | Age      | Gender     |");
            System.out.println("+------------+--------------------+----------+------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("| %-10s | %-18s | %-8s | %-10s |\n", id, name, age, gender);
                System.out.println("+------------+--------------------+----------+------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getDoctorById(int id) {
        String query = "SELECT * FROM Doctors WHERE Id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Returns true if doctor exists
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void viewDoctors() {
        String query = "SELECT * FROM Doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Doctors: ");
            System.out.println("+------------+--------------------+---------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization      |");
            System.out.println("+------------+--------------------+---------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                String specialization = resultSet.getString("Specialization");
                System.out.printf("| %-10s | %-18s | %-19s |\n", id, name, specialization);
                System.out.println("+------------+--------------------+---------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
