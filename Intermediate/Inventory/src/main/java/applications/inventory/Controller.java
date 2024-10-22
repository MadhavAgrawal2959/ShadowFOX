package applications.inventory;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonType;

public class Controller {

    @FXML
    private TableColumn<Product, String> colProductId;


    @FXML
    private TextField product_id, product_name, product_type, product_arrival, quantity, price, vendor_name;

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> colProductId, colProductName, colProductType, colProductArrival, colQuantity, colPrice, colVendorName;

    // List to store the products
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    // Method to handle "New" button click
    @FXML
    public void addNewProduct(ActionEvent event) {
        // Get the values from the text fields
        String id = product_id.getText();
        String name = product_name.getText();
        String type = product_type.getText();
        String arrival = product_arrival.getText();
        String quantityValue = quantity.getText();
        String priceValue = price.getText();
        String vendor = vendor_name.getText();

        // Add the new product to the list
        Product newProduct = new Product(id, name, type, arrival, quantityValue, priceValue, vendor);
        productList.add(newProduct);

        // Update the table
        productTable.setItems(productList);

        // Clear the fields after adding
        clearFields();
    }

    // Method to handle "Delete" button click
    @FXML
    public void deleteProduct(ActionEvent event) {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            // Confirm deletion
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                // Remove the selected product from the list
                productList.remove(selectedProduct);
            }
        } else {
            // If no product is selected, show an alert
            Alert alert = new Alert(Alert.AlertType.WARNING, "No product selected to delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    // Method to clear input fields
    private void clearFields() {
        product_id.clear();
        product_name.clear();
        product_type.clear();
        product_arrival.clear();
        quantity.clear();
        price.clear();
        vendor_name.clear();
    }

    // Initialize the table columns (bind table columns to product properties)
    @FXML
    public void initialize() {
        colProductId.setCellValueFactory(cellData -> cellData.getValue().productIdProperty());
        colProductName.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        colProductType.setCellValueFactory(cellData -> cellData.getValue().productTypeProperty());
        colProductArrival.setCellValueFactory(cellData -> cellData.getValue().productArrivalProperty());
        colQuantity.setCellValueFactory(cellData -> cellData.getValue().quantityProperty());
        colPrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        colVendorName.setCellValueFactory(cellData -> cellData.getValue().vendorNameProperty());

        // Initialize the product list and bind it to the table view
        productTable.setItems(productList);
    }

    // Nested class representing a Product
    public static class Product {
        private final StringProperty productId;
        private final StringProperty productName;
        private final StringProperty productType;
        private final StringProperty productArrival;
        private final StringProperty quantity;
        private final StringProperty price;
        private final StringProperty vendorName;

        public Product(String productId, String productName, String productType, String productArrival, String quantity, String price, String vendorName) {
            this.productId = new SimpleStringProperty(productId);
            this.productName = new SimpleStringProperty(productName);
            this.productType = new SimpleStringProperty(productType);
            this.productArrival = new SimpleStringProperty(productArrival);
            this.quantity = new SimpleStringProperty(quantity);
            this.price = new SimpleStringProperty(price);
            this.vendorName = new SimpleStringProperty(vendorName);
        }

        // Getters and property methods
        public String getProductId() {
            return productId.get();
        }

        public StringProperty productIdProperty() {
            return productId;
        }

        public String getProductName() {
            return productName.get();
        }

        public StringProperty productNameProperty() {
            return productName;
        }

        public String getProductType() {
            return productType.get();
        }

        public StringProperty productTypeProperty() {
            return productType;
        }

        public String getProductArrival() {
            return productArrival.get();
        }

        public StringProperty productArrivalProperty() {
            return productArrival;
        }

        public String getQuantity() {
            return quantity.get();
        }

        public StringProperty quantityProperty() {
            return quantity;
        }

        public String getPrice() {
            return price.get();
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getVendorName() {
            return vendorName.get();
        }

        public StringProperty vendorNameProperty() {
            return vendorName;
        }
    }
}
