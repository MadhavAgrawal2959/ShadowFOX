module com.example.fx_crud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.fx_crud to javafx.fxml;
    exports com.example.fx_crud;
}