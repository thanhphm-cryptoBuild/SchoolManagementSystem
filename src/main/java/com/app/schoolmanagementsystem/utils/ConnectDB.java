package com.app.schoolmanagementsystem.utils;

import java.sql.*;

public class ConnectDB {

    private static Connection connection;

    public static Connection connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/schoolmanagementsystem", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            connection = null;
        }
        return connection;
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static int countStudents() {
        String query = "SELECT COUNT(*) AS total FROM students WHERE Status = 'active'";
        try (Connection conn = connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int countStudentsByGender(boolean isMale) {
        String query = "SELECT COUNT(*) AS total FROM students WHERE Status = 'active' AND Gender = " + (isMale ? 1 : 0);
        try (Connection conn = connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
