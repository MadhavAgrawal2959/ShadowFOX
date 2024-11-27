package com.example.fx_crud;//package com.example.fx_crud;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLConnectionExample {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/testdb"; // Your DB name
        String username = "root"; // Your MySQL username
        String password = "1234"; // Your MySQL password

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establishing a connection to the database
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            System.out.println("Connected to MySQL database!");

            // SQL query to insert a new user (note: id and created_at are auto-handled)
            String sql = "INSERT INTO users (username, email) VALUES (?, ?)";

            // Creating a PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // Setting values for the username and email fields
            preparedStatement.setString(1, "new_user"); // Replace with dynamic value if needed
            preparedStatement.setString(2, "newuser@example.com"); // Replace with dynamic value if needed

            // Executing the SQL query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
