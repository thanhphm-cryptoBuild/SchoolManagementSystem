package com.app.schoolmanagementsystem.controller;

import com.app.schoolmanagementsystem.application.Application;
import com.app.schoolmanagementsystem.services.AuthService;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignInController implements Initializable {

    @FXML
    private Button btn;

    @FXML
    private StackPane changePWPane;

    @FXML
    private Label codeError;

    @FXML
    private TextField codeField;

    @FXML
    private Label confirmPassError;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private Label emailError;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailSendError;

    @FXML
    private TextField emailSendField;

    @FXML
    private ImageView eyeCloseImageView;

    @FXML
    private ImageView eyeOpenImageView;

    @FXML
    private StackPane forgotPWPane;

    @FXML
    private StackPane formForgotChange;

    @FXML
    private Label newPassError;

    @FXML
    private PasswordField newPassField;

    @FXML
    private AnchorPane pageSignIn;

    @FXML
    private Label passwordError;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordVisibleField;

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
        clearErrorMessages();

        String email = emailField.getText();
        String password = passwordField.getText();

        boolean isValid = true;


        // Kiểm tra email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty()) {
            emailError.setText("Email cannot be empty.");
            emailError.setVisible(true);
            isValid = false;
        } else if (!email.matches(emailRegex)) {
            emailError.setText("Invalid email format.");
            emailError.setVisible(true);
            isValid = false;
        }

        // Kiểm tra mật khẩu
        if (password == null || password.isEmpty()) {
            passwordError.setText("Password cannot be empty.");
            passwordError.setVisible(true);
            isValid = false;
        } else if (password.length() < 5) {
            passwordError.setText("Password must be at least 5 characters long.");
            passwordError.setVisible(true);
            isValid = false;
        }

        if (isValid) {
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
                passwordError.setText("Incorrect email or password.");
                showAlert(Alert.AlertType.INFORMATION, "Error", "Incorrect email or password.");
            }
        }
    }

    @FXML
    void closeFromForgotChange(MouseEvent event) {
        formForgotChange.setTranslateY(0);
        forgotPWPane.setVisible(false);
        changePWPane.setVisible(false);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.4));
        transition.setNode(formForgotChange);
        transition.setFromY(0);
        transition.setToY(750);
        transition.play();

        forgotPWPane.setTranslateX(-50);
        TranslateTransition backTransition = new TranslateTransition();
        backTransition.setDuration(Duration.seconds(0.3));
        backTransition.setNode(forgotPWPane);
        backTransition.setFromX(-50);
        backTransition.setToX(0);
        backTransition.play();
    }

    @FXML
    void openFormForgotChange(MouseEvent event) {
        formForgotChange.setTranslateY(500);
        formForgotChange.setVisible(true);
        forgotPWPane.setVisible(true);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(formForgotChange);
        transition.setFromY(750);
        transition.setToY(0);
        transition.play();
    }

    @FXML
    void sendCodeBTN(MouseEvent event) {
        clearErrorMessages();

        String email = emailSendField.getText();

        boolean isValid = true;

        // Kiểm tra email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty()) {
            emailSendError.setText("Email cannot be empty.");
            emailSendError.setVisible(true);
            isValid = false;

        } else if (!email.matches(emailRegex)) {
            emailSendError.setText("Invalid email format.");
            emailSendError.setVisible(true);
            isValid = false;
        }

        if (isValid) {
            try {
                authService.forgotPassword(email);

                // Hiển thị thông báo đợi
                showAlert(Alert.AlertType.INFORMATION, "Please Wait", "Code is being sent. You will be redirected shortly.");

                // Thực hiện chuyển trang sau một khoảng thời gian ngắn
                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(e -> {
                    forgotPWPane.setTranslateX(500);
                    forgotPWPane.setVisible(true);
                    changePWPane.setVisible(true);

                    TranslateTransition transition = new TranslateTransition();
                    transition.setDuration(Duration.seconds(0.3));
                    transition.setNode(changePWPane);
                    transition.setFromX(500);
                    transition.setToX(0);
                    transition.play();
                });
                pause.play();
            } catch (Exception e) {
                // Hiển thị thông báo lỗi nếu có lỗi
                emailSendError.setText("Email does not exist.");
                showAlert(Alert.AlertType.INFORMATION, "Error", "Email does not exist.");
                e.printStackTrace(); // In lỗi để dễ dàng debug
            }
        }
    }



    @FXML
    void submitFormForgotChange(MouseEvent event) {
        clearErrorMessages();

        String code = codeField.getText();
        String newPassword = newPassField.getText();
        String confirmPassword = confirmPassField.getText();

        boolean isValid = true;

        // Kiểm tra mã khôi phục
        if (code.isEmpty()) {
            codeError.setText("Reset code cannot be empty.");
            codeError.setVisible(true);
            isValid = false;
        }

        // Kiểm tra mật khẩu mới
        if (newPassword == null || newPassword.isEmpty()) {
            newPassError.setText("Password cannot be empty.");
            newPassError.setVisible(true);
            isValid = false;
        } else if (newPassword.length() < 5) {
            newPassError.setText("Password must be at least 5 characters long.");
            newPassError.setVisible(true);
            isValid = false;
        }

        // Kiểm tra mật khẩu xác nhận
        if (!newPassword.equals(confirmPassword)) {
            confirmPassError.setText("Passwords do not match.");
            confirmPassError.setVisible(true);
            isValid = false;
        }

        // Nếu form hợp lệ, bắt đầu xử lý mã khôi phục
        if (isValid) {
            try {
                // Lấy StaffID dựa trên mã khôi phục
                int staffID = getStaffIDByResetCode(code); // Giả sử bạn có phương thức để lấy staffID bằng mã khôi phục

                // Nếu staffID không tồn tại
                if (staffID == -1) {
                    showAlertAndRedirectToLogin("Invalid or expired code.");
                    return;
                }

                // Kiểm tra mã khôi phục
                if (!authService.validateResetCode(staffID, code)) {
                    showAlertAndRedirectToLogin("Invalid, expired, or already used reset code.");
                    return;
                }

                // Đặt lại mật khẩu nếu mã khôi phục hợp lệ
                if (authService.resetPassword(staffID, code, newPassword)) {
                    setResetCodeUsed(staffID); // Đánh dấu mã khôi phục đã sử dụng
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Password has been updated successfully.");
                    backToSignIn(); // Chuyển về trang đăng nhập
                } else {
                    showAlertAndRedirectToLogin("Failed to update password.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlertAndRedirectToLogin("An error occurred while resetting the password.");
            }
        }
    }

    private void showAlertAndRedirectToLogin(String message) {
        // Hiển thị thông báo lỗi
        showAlert(Alert.AlertType.ERROR, "Error", message);

        // Chuyển người dùng về form đăng nhập
        backToSignIn();
    }

    public void setResetCodeUsed(int staffID) throws SQLException {
        try (Connection conn = ConnectDB.connection()) {
            String query = "UPDATE Staffs SET IsResetCodeUsed = true WHERE StaffID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, staffID);
            stmt.executeUpdate();
        }
    }



    private int getStaffIDByResetCode(String code) throws SQLException {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT StaffID FROM Staffs WHERE ResetCode = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, code);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("StaffID");
            } else {
                return -1; // Trả về -1 nếu không tìm thấy mã khôi phục
            }
        }
    }

    private void backToSignIn() {
        // Quay lại màn hình đăng nhập
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("/com/app/schoolmanagementsystem/views/SignIn.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 750, 550);
            Stage stage = (Stage) pageSignIn.getScene().getWindow();
            stage.setResizable(false);
            stage.setTitle("School Management System");
            stage.setScene(scene);
            stage.centerOnScreen();

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void backToForgotPW(MouseEvent event) {
        forgotPWPane.setTranslateX(-50);
        forgotPWPane.setVisible(true);
        changePWPane.setVisible(false);

        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(forgotPWPane);
        transition.setFromX(-50);
        transition.setToX(0);
        transition.play();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
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

    private void clearErrorMessages() {
        emailError.setVisible(false);
        emailError.setText("");

        passwordError.setVisible(false);
        passwordError.setText("");

        emailSendError.setVisible(false);
        emailSendError.setText("");

        newPassError.setVisible(false);
        newPassError.setText("");

        confirmPassError.setVisible(false);
        confirmPassError.setText("");

        codeError.setVisible(false);
        codeError.setText("");

    }
}
