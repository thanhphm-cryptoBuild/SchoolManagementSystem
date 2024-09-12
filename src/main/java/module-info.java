module com.app.schoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.app.schoolmanagementsystem.controller to javafx.fxml;
    exports com.app.schoolmanagementsystem.application;
}