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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {

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

    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(new PropertyValueFactory<>("productId"));
        colProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        colProductType.setCellValueFactory(new PropertyValueFactory<>("productType"));
        colProductArrival.setCellValueFactory(new PropertyValueFactory<>("productArrival"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        colVendorName.setCellValueFactory(new PropertyValueFactory<>("vendorName"));

        // Load data from the database when initializing
        try {
            loadProductsFromDatabase();
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        tableView.setItems(productList);
    }

    private void loadProductsFromDatabase() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/inventory_management";
        String user = "root";
        String password = "1234";

        String sql = "SELECT * FROM inventory";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            productList.clear();

            while (rs.next()) {
                int productId = rs.getInt("Product_id");
                String productName = rs.getString("Product_Name");
                String productType = rs.getString("Product_type");
                String productArrival = rs.getString("Product_Arrival");
                int quantity = rs.getInt("Quantity_In_Stock");
                double productPrice = rs.getDouble("Product_Price");
                String vendorName = rs.getString("Product_Supplier");

                Product product = new Product(productId, productName, productType, productArrival, quantity, productPrice, vendorName);
                productList.add(product);
            }
        }
    }

    @FXML
    public void handleNewEntry() {
        try {
            int productId = Integer.parseInt(txtProductId.getText());
            String productName = txtProductName.getText();
            String productType = txtProductType.getText();
            String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            int quantity = Integer.parseInt(txtQuantity.getText());
            double productPrice = Double.parseDouble(txtProductPrice.getText());
            String vendorName = txtVendorName.getText();

            Product newProduct = new Product(productId, productName, productType, formattedDateTime, quantity, productPrice, vendorName);
            productList.add(newProduct);
            saveToDatabase(newProduct);
            clearInputFields();

        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid input format.");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void saveToDatabase(Product product) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/inventory_management";
        String user = "root";
        String password = "1234";

        String sql = "INSERT INTO inventory (Product_id, Product_Name, Product_type, Product_Arrival, Quantity_In_Stock, Product_Price, Product_Supplier) "
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

    @FXML
    public void handleChange() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            selectedProduct.setProductId(Integer.parseInt(txtProductId.getText()));
            selectedProduct.setProductName(txtProductName.getText());
            selectedProduct.setProductType(txtProductType.getText());

            tableView.refresh();
            updateDatabase(selectedProduct);
            clearInputFields();
        } else {
            System.out.println("No product selected for modification.");
        }
    }

    private void updateDatabase(Product product) {
        String url = "jdbc:mysql://localhost:3306/inventory_management";
        String user = "root";
        String password = "1234";

        String sql = "UPDATE inventory SET Product_Name=?, Product_type=?, Quantity_In_Stock=?, Product_Price=?, Product_Supplier=? WHERE Product_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductType());
            pstmt.setInt(3, product.getQuantity());
            pstmt.setDouble(4, product.getProductPrice());
            pstmt.setString(5, product.getVendorName());
            pstmt.setInt(6, product.getProductId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database update error: " + e.getMessage());
        }
    }

    @FXML
    public void handleDelete() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();

        if (selectedProduct != null) {
            productList.remove(selectedProduct);
            deleteFromDatabase(selectedProduct.getProductId());
            clearInputFields();
        } else {
            System.out.println("No product selected for deletion.");
        }
    }

    private void deleteFromDatabase(int productId) {
        String url = "jdbc:mysql://localhost:3306/inventory_management";
        String user = "root";
        String password = "1234";

        String sql = "DELETE FROM inventory WHERE Product_id=?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Database deletion error: " + e.getMessage());
        }
    }

    private void clearInputFields() {
        txtProductId.clear();
        txtProductName.clear();
        txtProductType.clear();
        txtQuantity.clear();
        txtProductPrice.clear();
        txtVendorName.clear();
    }
}
