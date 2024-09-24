package com.app.schoolmanagementsystem.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    public static int countStaffByGender(boolean isMale) {
        String query = "SELECT COUNT(*) AS total FROM staff WHERE Status = 'active' AND Gender = " + (isMale ? 1 : 0);
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

    public static List<Integer> getStaffData() {
        List<Integer> staffData = new ArrayList<>();
        String query = "SELECT COUNT(StaffID) AS count FROM staff WHERE Status = 'active'";
        try (Connection conn = connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                staffData.add(rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffData;
    }

    public static List<Integer> getStudentData() {
        List<Integer> studentData = new ArrayList<>();
        String query = "SELECT COUNT(StudentID) AS count FROM students WHERE Status = 'active'";
        try (Connection conn = connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                studentData.add(rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentData;
    }
    public static int countClasses() {
        int totalClasses = 0;
        String query = "SELECT COUNT(*) AS total FROM classes";
        try (Connection conn = connection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                totalClasses = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalClasses;
    }
}