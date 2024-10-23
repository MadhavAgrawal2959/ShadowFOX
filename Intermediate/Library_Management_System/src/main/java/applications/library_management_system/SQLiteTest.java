package applications.library_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteTest {
    public static void main(String[] args) {
        // SQLite connection string
        String url = "jdbc:sqlite:.db"; // This will create a 'sample.db' file in your project root

        // SQL statement for creating a new table
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    name TEXT NOT NULL,\n"
                + "    email TEXT NOT NULL\n"
                + ");";


    /**
     * Connect to the SQLite database
     * @return the Connection object
     */
    private static Connection connect() {
        Connection conn = null;
        try {
            // Establish SQLite connection
            String url = "jdbc:sqlite:";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
