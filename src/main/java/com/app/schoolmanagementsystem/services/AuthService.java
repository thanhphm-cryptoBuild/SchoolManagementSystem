package com.app.schoolmanagementsystem.services;

import com.app.schoolmanagementsystem.utils.Mail;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class AuthService {

    // Phương thức đăng nhập
    public boolean login(String email, String password) {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT Password, Status FROM Staffs WHERE Email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedPassword = rs.getString("Password");
                String status = rs.getString("Status");

                if (!"active".equals(status)) {
                    return false;
                }

                // So sánh mật khẩu người dùng nhập vào với mật khẩu đã mã hóa
                return BCrypt.checkpw(password, storedPassword);
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức quên mật khẩu
    public void forgotPassword(String email) throws Exception {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT StaffID FROM Staffs WHERE Email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int staffID = rs.getInt("StaffID");
                String resetCode = generateResetCode();
                LocalDateTime creationTime = LocalDateTime.now();

                String updateQuery = "UPDATE Staffs SET ResetCode = ?, ResetCodeCreationTime = ?, IsResetCodeUsed = FALSE WHERE StaffID = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setString(1, resetCode);
                updateStmt.setObject(2, creationTime);
                updateStmt.setInt(3, staffID);
                updateStmt.executeUpdate();

                String subject = "Password Reset Request";
                String messageBody = "To reset your password, use the following code: " + resetCode;
                Mail mail = new Mail();
                mail.sendEmail(email, subject, messageBody);
            } else {
                throw new Exception("Email không tồn tại");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Lỗi khi xử lý yêu cầu quên mật khẩu", e);
        }
    }

    public boolean validateResetCode(int staffID, String resetCode) throws SQLException {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT ResetCode, ResetCodeCreationTime, IsResetCodeUsed FROM Staffs WHERE StaffID = ?";
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
                            throw new SQLException("Mã khôi phục đã hết hạn");
                        }
                    } else {
                        throw new SQLException("Mã khôi phục đã được sử dụng");
                    }
                } else {
                    throw new SQLException("Mã khôi phục không hợp lệ");
                }
            } else {
                throw new SQLException("ID nhân viên không hợp lệ");
            }
        }
    }


    private String generateResetCode() {
        Random rand = new Random();
        int code = rand.nextInt(999999);
        return String.format("%06d", code);
    }

    // Phương thức đặt lại mật khẩu
    // Phương thức đặt lại mật khẩu
    public void resetPassword(int staffID, String resetCode, String newPassword) throws SQLException {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT ResetCode, ResetCodeCreationTime, IsResetCodeUsed FROM Staffs WHERE StaffID = ?";
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
                            // Mã hóa mật khẩu mới trước khi cập nhật
                            String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

                            // Cập nhật mật khẩu và reset trạng thái mã khôi phục
                            String updateQuery = "UPDATE Staffs SET Password = ?, ResetCode = NULL, ResetCodeCreationTime = NULL, IsResetCodeUsed = TRUE WHERE StaffID = ?";
                            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                            updateStmt.setString(1, hashedNewPassword);
                            updateStmt.setInt(2, staffID);
                            updateStmt.executeUpdate();
                        } else {
                            throw new SQLException("Mã khôi phục đã hết hạn");
                        }
                    } else {
                        throw new SQLException("Mã khôi phục đã được sử dụng");
                    }
                } else {
                    throw new SQLException("Mã khôi phục không hợp lệ");
                }
            } else {
                throw new SQLException("ID nhân viên không hợp lệ");
            }
        }
    }




    // Phương thức xác thực quyền dựa vào role
    public boolean hasRole(String email, String roleName) {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT sr.RoleName FROM Staffs s " +
                    "JOIN StaffRoles sr ON s.StaffID = sr.StaffID " +
                    "WHERE s.Email = ? AND sr.RoleName = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            stmt.setString(2, roleName);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức lấy RoleName của người dùng
    public String getRoleName(String email) {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT sr.RoleName FROM Staffs s " +
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
}