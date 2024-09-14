module com.app.schoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;requires javafx.base;
    requires javafx.graphics;

    opens com.app.schoolmanagementsystem.model to javafx.base;
    opens com.app.schoolmanagementsystem.controller to javafx.fxml;
    opens com.app.schoolmanagementsystem.application to javafx.graphics;

    exports com.app.schoolmanagementsystem.application;
}