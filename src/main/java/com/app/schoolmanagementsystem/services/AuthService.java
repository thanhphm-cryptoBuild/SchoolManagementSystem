package com.app.schoolmanagementsystem.services;

import com.app.schoolmanagementsystem.session.UserSession;
import com.app.schoolmanagementsystem.utils.Mail;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class AuthService {


    // Phương thức đăng nhập
    public boolean login(String email, String password) {
        // Validation email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(emailRegex)) {
            System.out.println("Invalid email format.");
            return false;
        }

        // Validation password
        if (password == null || password.isEmpty() || password.length() < 5) {
            System.out.println("Password must be at least 5 characters long.");
            return false;
        }

        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT Password, Status, sr.RoleName FROM Staff s " +
                    "JOIN StaffRoles sr ON s.StaffID = sr.StaffID " +
                    "WHERE s.Email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                String status = rs.getString("Status");
                String roleName = rs.getString("RoleName");

                if (!"active".equals(status)) {
                    return false;
                }

                // Set currentRoleName
                UserSession.setCurrentRoleName(roleName);

                // Compare user password with hashed password
                return BCrypt.checkpw(password, storedPassword);
//                return storedPassword.equals(password);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    // Phương thức quên mật khẩu
    public void forgotPassword(String email) throws Exception {
        // Validation email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(emailRegex)) {
            throw new Exception("Invalid email format.");
        }

        try (Connection conn = ConnectDB.connection()) {
            // Kiểm tra xem email có tồn tại trong cơ sở dữ liệu không
            String query = "SELECT StaffID FROM Staff WHERE Email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int staffID = rs.getInt("StaffID");
                String resetCode = generateResetCode();
                LocalDateTime creationTime = LocalDateTime.now();

                // Cập nhật mã khôi phục vào cơ sở dữ liệu
                String updateQuery = "UPDATE Staff SET ResetCode = ?, ResetCodeCreationTime = ?, IsResetCodeUsed = FALSE WHERE StaffID = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, resetCode);
                updateStmt.setObject(2, creationTime);
                updateStmt.setInt(3, staffID);
                updateStmt.executeUpdate();

                // Gửi email chứa mã khôi phục
                String subject = "Password Reset Request";
                String messageBody = "To reset your password, use the following code: " + resetCode;
                Mail mail = new Mail();
                mail.sendEmail(email, subject, messageBody);
            } else {
                // Email không tồn tại trong cơ sở dữ liệu
                throw new Exception("Email does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error processing password reset request.", e);
        }
    }




    public boolean validateResetCode(int staffID, String resetCode) throws SQLException {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT ResetCode, ResetCodeCreationTime, IsResetCodeUsed FROM Staff WHERE StaffID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, staffID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedResetCode = rs.getString("ResetCode");
                LocalDateTime creationTime = rs.getObject("ResetCodeCreationTime", LocalDateTime.class);
                boolean isResetCodeUsed = rs.getBoolean("IsResetCodeUsed");

                // So sánh mã khôi phục và kiểm tra trạng thái
                if (storedResetCode != null && storedResetCode.equals(resetCode)) {
                    if (!isResetCodeUsed) {
                        LocalDateTime now = LocalDateTime.now();
                        long minutesElapsed = ChronoUnit.MINUTES.between(creationTime, now);

                        if (minutesElapsed <= 1) { // 60 giây = 1 phút
                            return true; // Mã khôi phục hợp lệ
                        } else {
                            throw new SQLException("Reset code has expired.");
                        }
                    } else {
                        throw new SQLException("Reset code has already been used.");
                    }
                } else {
                    throw new SQLException("Invalid reset code.");
                }
            } else {
                throw new SQLException("Invalid staff ID.");
            }
        }
    }

    private String generateResetCode() {
        Random rand = new Random();
        int code = rand.nextInt(999999);
        return String.format("%06d", code);
    }

    // Phương thức đặt lại mật khẩu
    public boolean resetPassword(int staffID, String resetCode, String newPassword) throws SQLException {
        // Validation newPassword
        if (newPassword == null || newPassword.isEmpty() || newPassword.length() < 5) {
            throw new SQLException("Password must be at least 5 characters long.");
        }

        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT ResetCode, ResetCodeCreationTime, IsResetCodeUsed FROM Staff WHERE StaffID = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, staffID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedResetCode = rs.getString("ResetCode");
                LocalDateTime creationTime = rs.getObject("ResetCodeCreationTime", LocalDateTime.class);
                boolean isResetCodeUsed = rs.getBoolean("IsResetCodeUsed");

                // Compare reset code and check status
                if (storedResetCode != null && storedResetCode.equals(resetCode)) {
                    if (!isResetCodeUsed) {
                        LocalDateTime now = LocalDateTime.now();
                        long minutesElapsed = ChronoUnit.MINUTES.between(creationTime, now);

                        if (minutesElapsed <= 1) { // 60 seconds = 1 minute
                            // Hash new password before updating
                            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                            // Update password and reset reset code status
                            String updateQuery = "UPDATE Staff SET Password = ?, ResetCode = NULL, ResetCodeCreationTime = NULL, IsResetCodeUsed = TRUE WHERE StaffID = ?";
                            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                            updateStmt.setString(1, hashedNewPassword);
                            updateStmt.setInt(2, staffID);
                            int rowsUpdated = updateStmt.executeUpdate();

                            return rowsUpdated > 0; // Return true if at least one row was updated
                        } else {
                            throw new SQLException("Reset code has expired.");
                        }
                    } else {
                        throw new SQLException("Reset code has already been used.");
                    }
                } else {
                    throw new SQLException("Invalid reset code.");
                }
            } else {
                throw new SQLException("Invalid staff ID.");
            }
        }
    }


    public boolean hasRole(String email, String roleName) {
        // Validation email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(emailRegex)) {
            return false;
        }

        // Validation roleName
        if (roleName == null || roleName.isEmpty()) {
            return false;
        }

        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT sr.RoleName FROM Staff s " +
                    "JOIN StaffRoles sr ON s.StaffID = sr.StaffID " +
                    "WHERE s.Email = ? AND sr.RoleName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, roleName);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Nếu có bản ghi, người dùng có quyền
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void handleDashboardAccess(String email) {
        AuthService authService = new AuthService();

        if (authService.hasRole(email, "Admin Master")) {
            System.out.println("Access granted to Admin Master.");
            // Mở giao diện dành cho Admin Master
        } else if (authService.hasRole(email, "Manager")) {
            System.out.println("Access granted to Manager.");
            // Mở giao diện dành cho Manager
        } else if (authService.hasRole(email, "Teacher")) {
            System.out.println("Access granted to Teacher.");
            // Mở giao diện dành cho Teacher
        } else {
            System.out.println("Access denied.");
            // Hiển thị thông báo từ chối truy cập
        }
    }



    // Phương thức lấy RoleName của người dùng
    public String getRoleName(String email) {
        // Validation email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null || email.isEmpty() || !email.matches(emailRegex)) {
            return null;
        }

        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT sr.RoleName FROM Staff s " +
                    "JOIN StaffRoles sr ON s.StaffID = sr.StaffID " +
                    "WHERE s.Email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("RoleName");
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasAccess(String roleName, String page) {
        switch (roleName) {
            case "Admin Master":
                return true; // Admin Master có quyền truy cập tất cả các trang

            case "Manager":
                return List.of(
                        "PageDashBoard.fxml",
                        "PageCalendar.fxml",
                        "PageStaff.fxml",
                        "PageStudent.fxml",
                        "PageClass.fxml",
                        "PageSubject.fxml",
                        "PageTuition.fxml",
                        "PageAdvanced.fxml"
                ).contains(page);

            case "Teacher":
                return !List.of(
                        "PageAdvanced.fxml",
                        "PageStaff.fxml",
                        "PageSubject.fxml",
                        "PageTuition.fxml",
                        "PageAdvanced.fxml"
                ).contains(page);

            default:
                return false; // Quyền truy cập không được xác định cho các vai trò khác
        }
    }


}