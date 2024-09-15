package com.app.schoolmanagementsystem.services;

import com.app.schoolmanagementsystem.utils.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class PasswordResetTest {

    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Scanner scanner = new Scanner(System.in);

        try {
            // Bước 1: Yêu cầu người dùng nhập email để yêu cầu mã khôi phục
            System.out.print("Nhập email của bạn để yêu cầu mã khôi phục: ");
            String email = scanner.nextLine();

            // Gửi mã khôi phục đến email
            authService.forgotPassword(email);
            System.out.println("Mã khôi phục đã được gửi đến email của bạn.");

            // Bước 2: Nhập mã khôi phục
            System.out.print("Nhập mã khôi phục bạn nhận được: ");
            String resetCode = scanner.nextLine();

            // Tìm StaffID từ email
            int staffID = getStaffIDByEmail(email);

            // Xác thực mã khôi phục và yêu cầu mật khẩu mới nếu mã hợp lệ
            boolean isResetCodeValid = authService.validateResetCode(staffID, resetCode);
            if (isResetCodeValid) {
                System.out.print("Nhập mật khẩu mới: ");
                String newPassword = scanner.nextLine();

                // Đặt lại mật khẩu nếu mã khôi phục hợp lệ và chưa hết hạn
                try {
                    authService.resetPassword(staffID, resetCode, newPassword);
                    System.out.println("Mật khẩu của bạn đã được đặt lại thành công.");
                } catch (SQLException e) {
                    System.out.println("Đã xảy ra lỗi khi đặt lại mật khẩu: " + e.getMessage());
                    System.exit(1); // Thoát chương trình nếu có lỗi khi đặt lại mật khẩu
                }
            } else {
                System.out.println("Mã khôi phục không hợp lệ hoặc đã hết hạn.");
                System.exit(1); // Thoát chương trình nếu mã khôi phục không hợp lệ
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private static int getStaffIDByEmail(String email) throws SQLException {
        try (Connection conn = ConnectDB.connection()) {
            String query = "SELECT StaffID FROM Staffs WHERE Email = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("StaffID");
            } else {
                throw new SQLException("Email không tồn tại");
            }
        }
    }
}