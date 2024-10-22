package applications.inventory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySql_Connection {

    // Database connection details
    String url = "jdbc:mysql://localhost:3306/inventory_management";
    String user = "root";
    String password = "1234";

    // JDBC variables
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    // Constructor to establish connection
    public MySql_Connection() {
        try {
            // Establishing a connection to the database
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to MySQL database!");

            // You can initialize your preparedStatement here with some SQL query
            // For example:
            // String sql = "INSERT INTO products (product_id, product_name) VALUES (?, ?)";
            // preparedStatement = connection.prepareStatement(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Ensuring resources are closed properly
            closeResources();
        }
    }

    // Method to close resources
    private void closeResources() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Main method to test the connection
    public static void main(String[] args) {
        new MySql_Connection();
    }
}
