package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffModel {

    // Phương thức đếm số lượng nhân viên
    public int countStaff() {
        int count = 0;
        String query = "SELECT COUNT(*) AS totalStaff FROM staff";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                count = resultSet.getInt("totalStaff");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // Phương thức lấy danh sách tất cả nhân viên
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Staff staff = new Staff(
                        resultSet.getInt("staffID"),
                        resultSet.getString("fullName"),
                        resultSet.getString("address"),
                        resultSet.getString("phone"),
                        resultSet.getString("email"),
                        resultSet.getDate("birthDate"),
                        resultSet.getString("department"),
                        resultSet.getString("position"),
                        resultSet.getDate("hireDate"),
                        resultSet.getBigDecimal("salary"),
                        resultSet.getDate("lastLogin"),
                        resultSet.getString("profilePicture"),
                        resultSet.getString("sex"),
                        resultSet.getString("status")
                );
                staffList.add(staff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    public boolean addStaff(Staff staff) {
        String query = "INSERT INTO staff (fullName, address, phone, email, birthDate, department, position, hireDate, salary, lastLogin, profilePicture, sex, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, staff.getFullName());
            preparedStatement.setString(2, staff.getAddress());
            preparedStatement.setString(3, staff.getPhone());
            preparedStatement.setString(4, staff.getEmail());
            preparedStatement.setDate(5, staff.getBirthDate());
            preparedStatement.setString(6, staff.getDepartment());
            preparedStatement.setString(7, staff.getPosition());
            preparedStatement.setDate(8, staff.getHireDate());
            preparedStatement.setBigDecimal(9, staff.getSalary());
            preparedStatement.setDate(10, staff.getLastLogin());
            preparedStatement.setString(11, staff.getProfilePicture());
            preparedStatement.setString(12, staff.getSex());
            preparedStatement.setString(13, staff.getStatus());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức cập nhật thông tin nhân viên
    public boolean updateStaff(Staff staff) {
        String query = "UPDATE staff SET fullName = ?, address = ?, phone = ?, email = ?, birthDate = ?, department = ?, position = ?, hireDate = ?, salary = ?, lastLogin = ?, profilePicture = ?, sex = ?, status = ? WHERE staffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, staff.getFullName());
            preparedStatement.setString(2, staff.getAddress());
            preparedStatement.setString(3, staff.getPhone());
            preparedStatement.setString(4, staff.getEmail());
            preparedStatement.setDate(5, staff.getBirthDate());
            preparedStatement.setString(6, staff.getDepartment());
            preparedStatement.setString(7, staff.getPosition());
            preparedStatement.setDate(8, staff.getHireDate());
            preparedStatement.setBigDecimal(9, staff.getSalary());
            preparedStatement.setDate(10, staff.getLastLogin());
            preparedStatement.setString(11, staff.getProfilePicture());
            preparedStatement.setString(12, staff.getSex());
            preparedStatement.setString(13, staff.getStatus());
            preparedStatement.setInt(14, staff.getStaffID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa nhân viên theo ID
    public boolean deleteStaff(int staffID) {
        String query = "DELETE FROM staff WHERE staffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, staffID);
            int rowsAffected = preparedStatement.executeUpdate();

            // Trả về true nếu xóa thành công (có ít nhất 1 hàng bị ảnh hưởng)
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về false nếu không xóa thành công
        return false;
    }
}
