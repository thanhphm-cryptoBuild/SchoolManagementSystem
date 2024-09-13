package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.application.Application;
import com.app.schoolmanagementsystem.services.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private AnchorPane pageSignIn;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordVisibleField;

    @FXML
    private ImageView eyeOpenImageView;

    @FXML
    private ImageView eyeCloseImageView;

    private boolean isPasswordVisible = false;

    private AuthService authService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        authService = new AuthService(); // Khởi tạo AuthService

        // Đặt trạng thái ban đầu
        passwordVisibleField.setVisible(false);
        eyeCloseImageView.setVisible(false);

        // Liên kết dữ liệu giữa TextField và PasswordField
        passwordVisibleField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    @FXML
    void loginSubmit(MouseEvent event) throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (authService.login(email, password)) {
            // Lấy vai trò của người dùng
            String roleName = authService.getRoleName(email);

            // Nếu đăng nhập thành công
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/app/schoolmanagementsystem/views/Dashboard.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 1366, 780);

                // Truyền vai trò tới DashboardController
                Controller dashboardController = fxmlLoader.getController();
                dashboardController.setRoleName(roleName);

                Stage stage = (Stage) pageSignIn.getScene().getWindow();
                stage.setResizable(false);
                stage.setTitle("School Management System");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Nếu đăng nhập thất bại, hiển thị thông báo lỗi
            showAlert(AlertType.ERROR, "Login failed", "Incorrect account or password.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleEyeOpenClick() {
        if (!isPasswordVisible) {
            showPassword();
        }
    }

    @FXML
    public void handleEyeCloseClick() {
        if (isPasswordVisible) {
            hidePassword();
        }
    }

    private void showPassword() {
        isPasswordVisible = true;
        passwordField.setVisible(false);
        passwordVisibleField.setVisible(true);
        eyeOpenImageView.setVisible(false);
        eyeCloseImageView.setVisible(true);
    }

    private void hidePassword() {
        isPasswordVisible = false;
        passwordVisibleField.setVisible(false);
        passwordField.setVisible(true);
        eyeOpenImageView.setVisible(true);
        eyeCloseImageView.setVisible(false);
    }
}
