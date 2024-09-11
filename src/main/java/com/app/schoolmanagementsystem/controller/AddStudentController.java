package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.model.Student;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.time.LocalDate;

public class AddStudentController {

    @FXML private TextField nameField;
    @FXML private TextField addressField;
    @FXML private TextField phoneField;
    @FXML private TextField emailField;
    @FXML private DatePicker birthDatePicker;
    @FXML private TextField registrationNoField;
    @FXML private TextField batchField;
    @FXML private DatePicker admissionDatePicker;
    @FXML private TextField previousInstituteField;
    @FXML private ComboBox<String> sexComboBox;

    @FXML
    private void initialize() {
        sexComboBox.getItems().addAll("male", "female");
        sexComboBox.setValue("male"); // Đặt giá trị mặc định là "Nam"
    }

    @FXML
    private void addStudent() {
        if (validateInputs()) {
            try (Connection conn = ConnectDB.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO student (fullName, address, phone, email, birthDate, registrationNo, batch, admissionDate, previousInstitute, sex) " +
                                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

                stmt.setString(1, nameField.getText());
                stmt.setString(2, addressField.getText());
                stmt.setString(3, phoneField.getText());
                stmt.setString(4, emailField.getText());
                stmt.setDate(5, Date.valueOf(birthDatePicker.getValue()));
                stmt.setString(6, registrationNoField.getText());
                stmt.setString(7, batchField.getText());
                stmt.setDate(8, Date.valueOf(admissionDatePicker.getValue()));
                stmt.setString(9, previousInstituteField.getText());
                stmt.setString(10, sexComboBox.getValue());

                int affectedRows = stmt.executeUpdate();

                if (affectedRows > 0) {
                    showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã thêm sinh viên mới.");
                    closeWindow();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Lỗi", "Không thể thêm sinh viên. Vui lòng thử lại.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Lỗi", "Đã xảy ra lỗi khi thêm sinh viên: " + e.getMessage());
            }
        }
    }

    private boolean validateInputs() {
        if (nameField.getText().isEmpty() || addressField.getText().isEmpty() || phoneField.getText().isEmpty() ||
                emailField.getText().isEmpty() || birthDatePicker.getValue() == null || registrationNoField.getText().isEmpty() ||
                batchField.getText().isEmpty() || admissionDatePicker.getValue() == null || sexComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Lỗi", "Vui lòng điền đầy đủ thông tin.");
            return false;
        }
        return true;
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}