//package applications.inventory;
//
//
//import javafx.beans.property.*;
//
//public class Product {
//    private final IntegerProperty productId;
//    private final StringProperty productName;
//    private final StringProperty productType;
//    private final IntegerProperty quantity;
//    private final DoubleProperty price;
//    private final StringProperty productSupplier;
//
//    public Product(int productId, String productName, String productType, int quantity, double price, String productSupplier) {
//        this.productId = new SimpleIntegerProperty(productId);
//        this.productName = new SimpleStringProperty(productName);
//        this.productType = new SimpleStringProperty(productType);
//        this.quantity = new SimpleIntegerProperty(quantity);
//        this.price = new SimpleDoubleProperty(price);
//        this.productSupplier = new SimpleStringProperty(productSupplier);
//    }
//
//    // Getters
//    public IntegerProperty productIdProperty() { return productId; }
//    public StringProperty productNameProperty() { return productName; }
//    public StringProperty productTypeProperty() { return productType; }
//    public IntegerProperty quantityProperty() { return quantity; }
//    public DoubleProperty priceProperty() { return price; }
//    public StringProperty productSupplierProperty() { return productSupplier; }
//}


package applications.inventory;

public class Product {
    private int productId;
    private String productName;
    private String productType;
    private String productArrival;
    private int quantity;
    private double productPrice;
    private String vendorName;

    public Product(int productId, String productName, String productType, String productArrival, int quantity, double productPrice, String vendorName) {
        this.productId = productId;
        this.productName = productName;
        this.productType = productType;
        this.productArrival = productArrival;
        this.quantity = quantity;
        this.productPrice = productPrice;
        this.vendorName = vendorName;
    }

    // Getters and setters for each field
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductArrival() {
        return productArrival;
    }

    public void setProductArrival(String productArrival) {
        this.productArrival = productArrival;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
