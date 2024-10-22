package applications.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {

    // FXML Components: TableView and Columns
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> colProductId;
    @FXML
    private TableColumn<Product, String> colProductName;
    @FXML
    private TableColumn<Product, String> colProductType;
    @FXML
    private TableColumn<Product, String> colProductArrival;
    @FXML
    private TableColumn<Product, Integer> colQuantity;
    @FXML
    private TableColumn<Product, Double> colProductPrice;
    @FXML
    private TableColumn<Product, String> colVendorName;

    // FXML Text Fields for user input
    @FXML
    private TextField txtProductId;
    @FXML
    private TextField txtProductName;
    @FXML
    private TextField txtProductType;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtProductPrice;
    @FXML
    private TextField txtVendorName;

    // Observable list to hold product data
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the table columns to bind to Product properties
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        colProductArrival.setCellValueFactory(new PropertyValueFactory<>("productArrival"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        colVendorName.setCellValueFactory(new PropertyValueFactory<>("vendorName"));

        // Load initial data into the TableView
        tableView.setItems(productList);
    }

    // Method to handle new product entry
    @FXML
    public void handleNewEntry() {
        try {
            // Extract input values
            int productId = Integer.parseInt(txtProductId.getText());
            String productName = txtProductName.getText();
            String productType = txtProductType.getText();
            String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            int quantity = Integer.parseInt(txtQuantity.getText());
            double productPrice = Double.parseDouble(txtProductPrice.getText());
            String vendorName = txtVendorName.getText();

            // Create a new Product object
            Product newProduct = new Product(productId, productName, productType, formattedDateTime, quantity, productPrice, vendorName);

            // Add product to list and save it to the database
            productList.add(newProduct);
            saveToDatabase(newProduct);

            // Clear input fields after entry
            clearInputFields();

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input format.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Method to save product to MySQL database
    private void saveToDatabase(Product product) throws SQLException {
        // Connection details for the database
        String url = "jdbc:mysql://localhost:3306/inventory_management"; // Update with your database name
        String user = "root"; // Update with your MySQL username
        String password = "1234"; // Update with your MySQL password

        // SQL query to insert new product
        String sql = "INSERT INTO products (Product_id, Product_Name, Product_type, Product_Arrival, Quantity_In_Stock, Product_Price, Product_Supplier) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getProductId());
            pstmt.setString(2, product.getProductName());
            pstmt.setString(3, product.getProductType());
            pstmt.setString(4, product.getProductArrival());
            pstmt.setInt(5, product.getQuantity());
            pstmt.setDouble(6, product.getProductPrice());
            pstmt.setString(7, product.getVendorName());
            pstmt.executeUpdate();
        }
    }

    // Method to handle product modification
    @FXML
    public void handleChange() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Update product details from text fields
            selectedProduct.setProductId(Integer.parseInt(txtProductId.getText()));
            selectedProduct.setProductName(txtProductName.getText());
            selectedProduct.setProductType(txtProductType.getText());

            // Refresh the TableView to reflect changes
            tableView.refresh();

            // Clear input fields after modification
            clearInputFields();
        } else {
            System.out.println("No product selected for modification.");
        }
    }

    // Method to handle product deletion
    @FXML
    public void handleDelete() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            // Remove selected product from the list
            productList.remove(selectedProduct);

            // Clear input fields after deletion
            clearInputFields();
        } else {
            System.out.println("No product selected for deletion.");
        }
    }

    // Clear all input fields after an action
    private void clearInputFields() {
        txtProductId.clear();
        txtProductName.clear();
        txtProductType.clear();
        txtQuantity.clear();
        txtProductPrice.clear();
        txtVendorName.clear();
    }
}
