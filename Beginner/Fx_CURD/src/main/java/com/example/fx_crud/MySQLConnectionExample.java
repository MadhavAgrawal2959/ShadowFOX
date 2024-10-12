package com.example.fx_crud;

import java.sql.*;

public class MySQLConnectionExample {
    public static void main(String[] args) {
        // JDBC URL format: jdbc:mysql://<host>:<port>/<database>
        String jdbcUrl = "jdbc:mysql://localhost:3306/testdb";
        String username = "root"; // Your MySQL username
        String password = "1234"; // Your MySQL password

        try {
            // Establishing a connection to the database
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connection successful!");

            // Perform your database operations here

            // Close the connection

            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            // Process the result set
            while (resultSet.next()) {
                System.out.println("Column1: " + resultSet.getString("username"));
                System.out.println("Column2: " + resultSet.getInt(""));
            }
            connection.close();
        } catch (SQLException e) {
            // Handle SQL exceptions (e.g., wrong credentials, wrong database URL)
            e.printStackTrace();
        }
    }
}
