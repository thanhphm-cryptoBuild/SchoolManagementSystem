module com.app.schoolmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.app.schoolmanagementsystem.controller to javafx.fxml;
    exports com.app.schoolmanagementsystem.application;
}