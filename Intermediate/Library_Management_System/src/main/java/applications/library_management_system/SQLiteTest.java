package applications.library_management_system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteTest {
    public static void main(String[] args) {
        // SQLite connection string
        String url = "jdbc:sqlite:sample.db"; // This will create a 'sample.db' file in your project root

        // Create tables
        createNewTables();

        // Insert sample data into the tables
        insertUser("John Doe", "john@example.com");
        insertBook("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Fiction");
        insertTransaction(1, 1, "2024-10-20", null);  // Borrow without a return date
        insertRecommendation(1, 1);

        // Verify by printing the data from the tables
        selectAllUsers();
        selectAllBooks();
        selectAllTransactions();
        selectAllRecommendations();
    }

    /**
     * Connect to the SQLite database
     * @return the Connection object
     */
    private static Connection connect() {
        Connection conn = null;
        try {
            // Establish SQLite connection
            String url = "jdbc:sqlite:sample.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Create new tables in the database
     */
    private static void createNewTables() {
        String createUsersTableSQL = "CREATE TABLE IF NOT EXISTS users (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    name TEXT NOT NULL,\n"
                + "    email TEXT NOT NULL\n"
                + ");";
        String createBooksTableSQL = "CREATE TABLE IF NOT EXISTS books (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    title TEXT NOT NULL,\n"
                + "    author TEXT NOT NULL,\n"
                + "    isbn TEXT UNIQUE NOT NULL,\n"
                + "    genre TEXT,\n"
                + "    availability INTEGER DEFAULT 1\n"
                + ");";
        String createTransactionsTableSQL = "CREATE TABLE IF NOT EXISTS transactions (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    user_id INTEGER NOT NULL,\n"
                + "    book_id INTEGER NOT NULL,\n"
                + "    borrow_date TEXT,\n"
                + "    return_date TEXT,\n"
                + "    FOREIGN KEY (user_id) REFERENCES users(id),\n"
                + "    FOREIGN KEY (book_id) REFERENCES books(id)\n"
                + ");";
        String createRecommendationsTableSQL = "CREATE TABLE IF NOT EXISTS recommendations (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + "    user_id INTEGER NOT NULL,\n"
                + "    book_id INTEGER NOT NULL,\n"
                + "    FOREIGN KEY (user_id) REFERENCES users(id),\n"
                + "    FOREIGN KEY (book_id) REFERENCES books(id)\n"
                + ");";

        // Execute the SQL to create tables
        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTableSQL);
            stmt.execute(createBooksTableSQL);
            stmt.execute(createTransactionsTableSQL);
            stmt.execute(createRecommendationsTableSQL);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert data into the Users table
     */
    private static void insertUser(String name, String email) {
        String sql = "INSERT INTO users(name, email) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert data into the Books table
     */
    private static void insertBook(String title, String author, String isbn, String genre) {
        String sql = "INSERT INTO books(title, author, isbn, genre) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, genre);
            pstmt.executeUpdate();
            System.out.println("Book inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert data into the Transactions table
     */
    private static void insertTransaction(int userId, int bookId, String borrowDate, String returnDate) {
        String sql = "INSERT INTO transactions(user_id, book_id, borrow_date, return_date) VALUES(?, ?, ?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.setString(3, borrowDate);
            pstmt.setString(4, returnDate);
            pstmt.executeUpdate();
            System.out.println("Transaction inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert data into the Recommendations table
     */
    private static void insertRecommendation(int userId, int bookId) {
        String sql = "INSERT INTO recommendations(user_id, book_id) VALUES(?, ?)";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            pstmt.executeUpdate();
            System.out.println("Recommendation inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select all users from the Users table
     */
    private static void selectAllUsers() {
        String sql = "SELECT * FROM users";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Users:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("name") + "\t" + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select all books from the Books table
     */
    private static void selectAllBooks() {
        String sql = "SELECT * FROM books";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Books:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("title") + "\t" + rs.getString("author"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select all transactions from the Transactions table
     */
    private static void selectAllTransactions() {
        String sql = "SELECT * FROM transactions";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Transactions:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getInt("user_id") + "\t" + rs.getInt("book_id") + "\t" + rs.getString("borrow_date") + "\t" + rs.getString("return_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Select all recommendations from the Recommendations table
     */
    private static void selectAllRecommendations() {
        String sql = "SELECT * FROM recommendations";

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Recommendations:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getInt("user_id") + "\t" + rs.getInt("book_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
