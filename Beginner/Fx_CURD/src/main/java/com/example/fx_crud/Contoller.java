package com.example.fx_crud;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Contoller {

    @FXML
    private TextField Firstname;

    @FXML
    private TextField MiddleName;

    @FXML
    private TextField LastName;

    @FXML
    private TextField PhoneNumber;

    @FXML
    private TextField Email;

    @FXML
    private Button New_Data;

    @FXML
    private Button Save_Data;

    @FXML
    private Button Update_Data;

    @FXML
    private Button Delete_Data;

    @FXML
    private TextField Search_Data;

    // Database connection parameters
    private String jdbcUrl = "jdbc:mysql://localhost:3306/testdb";  // Replace with your DB name
    private String dbUser = "root";  // Your MySQL username
    private String dbPassword = "1234";  // Your MySQL password

    // Method to insert data into the database when "New" button is clicked
    @FXML
    private void Add_Data_database() {
        // Get data from text fields
        String firstName = Firstname.getText();
        String middleName = MiddleName.getText();
        String lastName = LastName.getText();
        String phoneNumber = PhoneNumber.getText();
        String email = Email.getText();

        // Insert query
        String query = "INSERT INTO users (FirstName, MiddleName, LastName, PhoneNumber, Email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set the parameters in the query
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, middleName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, email);

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data inserted successfully!");
            } else {
                System.out.println("Failed to insert data.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
