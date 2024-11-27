module applications.library_management_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens applications.library_management_system to javafx.fxml;
    exports applications.library_management_system;
}