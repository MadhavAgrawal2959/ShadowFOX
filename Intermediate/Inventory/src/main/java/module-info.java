module applications.inventory {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens applications.inventory to javafx.fxml;
    exports applications.inventory;
}