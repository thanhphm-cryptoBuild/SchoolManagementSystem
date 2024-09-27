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
            String email = scanner.nextLine();

            authService.forgotPassword(email);
            String resetCode = scanner.nextLine();

            int staffID = getStaffIDByEmail(email);

            boolean isResetCodeValid = authService.validateResetCode(staffID, resetCode);
            if (isResetCodeValid) {
                String newPassword = scanner.nextLine();

                try {
                    authService.resetPassword(staffID, resetCode, newPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            } else {
                System.exit(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
                throw new SQLException("Email does not exist");
            }
        }
    }
}