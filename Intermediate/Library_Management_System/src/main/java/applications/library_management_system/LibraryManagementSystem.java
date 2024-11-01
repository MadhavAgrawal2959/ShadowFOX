// Import necessary packages
package applications.library_management_system;

import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add User");
            System.out.println("2. Add Book");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. View All Users");
            System.out.println("6. View All Books");
            System.out.println("7. View All Transactions");
            System.out.println("8. Exit");

            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter user email: ");
                    String email = scanner.nextLine();
                    library.insertUser(name, email);
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter book genre: ");
                    String genre = scanner.nextLine();
                    library.insertBook(title, author, isbn, genre);
                    break;
                case 3:
                    System.out.print("Enter user ID: ");
                    int userId = scanner.nextInt();
                    System.out.print("Enter book ID: ");
                    int bookId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter borrow date (YYYY-MM-DD): ");
                    String borrowDate = scanner.nextLine();
                    library.insertTransaction(userId, bookId, borrowDate, null);
                    break;
                case 4:
                    System.out.print("Enter transaction ID: ");
                    int transactionId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter return date (YYYY-MM-DD): ");
                    String returnDate = scanner.nextLine();
                    library.returnBook(transactionId, returnDate);
                    break;
                case 5:
                    library.selectAllUsers();
                    break;
                case 6:
                    library.selectAllBooks();
                    break;
                case 7:
                    library.selectAllTransactions();
                    break;
                case 8:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static final String DATABASE_URL = "jdbc:sqlite:lib_manag_system.db";

    public LibraryManagementSystem() {
        createNewTables();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connection to SQLite established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private void createNewTables() {
        String createUsersTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT NOT NULL
            );
            """;
        String createBooksTableSQL = """
            CREATE TABLE IF NOT EXISTS books (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                author TEXT NOT NULL,
                isbn TEXT UNIQUE NOT NULL,
                genre TEXT,
                availability INTEGER DEFAULT 1
            );
            """;
        String createTransactionsTableSQL = """
            CREATE TABLE IF NOT EXISTS transactions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                book_id INTEGER NOT NULL,
                borrow_date TEXT,
                return_date TEXT,
                FOREIGN KEY (user_id) REFERENCES users(id),
                FOREIGN KEY (book_id) REFERENCES books(id)
            );
            """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTableSQL);
            stmt.execute(createBooksTableSQL);
            stmt.execute(createTransactionsTableSQL);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertUser(String name, String email) {
        String sql = "INSERT INTO users(name, email) VALUES(?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
            System.out.println("User added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertBook(String title, String author, String isbn, String genre) {
        String sql = "INSERT INTO books(title, author, isbn, genre) VALUES(?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setString(3, isbn);
            pstmt.setString(4, genre);
            pstmt.executeUpdate();
            System.out.println("Book added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertTransaction(int userId, int bookId, String borrowDate, String returnDate) {
        String checkAvailabilitySQL = "SELECT availability FROM books WHERE id = ?";
        String insertTransactionSQL = "INSERT INTO transactions(user_id, book_id, borrow_date, return_date) VALUES(?, ?, ?, ?)";
        String updateBookAvailabilitySQL = "UPDATE books SET availability = 0 WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkAvailabilitySQL);
             PreparedStatement insertStmt = conn.prepareStatement(insertTransactionSQL);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookAvailabilitySQL)) {

            // Check if the book is available
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt("availability") == 1) {
                // Book is available, proceed with transaction and mark as borrowed
                insertStmt.setInt(1, userId);
                insertStmt.setInt(2, bookId);
                insertStmt.setString(3, borrowDate);
                insertStmt.setString(4, returnDate);
                insertStmt.executeUpdate();

                updateBookStmt.setInt(1, bookId);
                updateBookStmt.executeUpdate();

                System.out.println("Transaction recorded successfully. Book borrowed.");
            } else {
                System.out.println("Book is not available for borrowing.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnBook(int transactionId, String returnDate) {
        String checkTransactionSQL = "SELECT book_id FROM transactions WHERE id = ? AND return_date IS NULL";
        String updateTransactionSQL = "UPDATE transactions SET return_date = ? WHERE id = ?";
        String updateBookAvailabilitySQL = "UPDATE books SET availability = 1 WHERE id = ?";

        try (Connection conn = connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkTransactionSQL);
             PreparedStatement updateTransactionStmt = conn.prepareStatement(updateTransactionSQL);
             PreparedStatement updateBookStmt = conn.prepareStatement(updateBookAvailabilitySQL)) {

            // Check if the transaction exists and book is borrowed
            checkStmt.setInt(1, transactionId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                // Update transaction to mark as returned
                updateTransactionStmt.setString(1, returnDate);
                updateTransactionStmt.setInt(2, transactionId);
                updateTransactionStmt.executeUpdate();

                // Set book availability back to available
                updateBookStmt.setInt(1, bookId);
                updateBookStmt.executeUpdate();

                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Invalid transaction ID or book is already returned.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void selectAllUsers() {
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

    public void selectAllBooks() {
        String sql = "SELECT * FROM books";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Books:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString("title") + "\t" + rs.getString("author") + "\t" + rs.getString("isbn") + "\t" + rs.getString("genre") + "\t" + rs.getInt("availability"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAllTransactions() {
        String sql = "SELECT * FROM transactions";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("Transactions:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\tUser ID: " + rs.getInt("user_id") + "\tBook ID: " + rs.getInt("book_id") + "\tBorrow Date: " + rs.getString("borrow_date") + "\tReturn Date: " + rs.getString("return_date"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
