module com.app.schoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;

    opens com.app.schoolmanagementsystem.entities to javafx.base;
    opens com.app.schoolmanagementsystem.controller to javafx.fxml;

    exports com.app.schoolmanagementsystem.application;
}