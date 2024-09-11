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

    // Phương thức đếm số lượng nhân viên trang thai active
    public int countActiveStaff() {
        int count = 0;
        String query = "SELECT COUNT(*) AS totalActiveStaff FROM staff WHERE status = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, "active");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("totalActiveStaff");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


    // Phương thức lấy danh sách tất cả nhân viên trang thai active
    public List<Staff> getActiveStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM staff WHERE status = 'active'";

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

    // phuong thuc add staff voi trang thai mac dinh la active
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

            // Nếu không có giá trị status được cung cấp, mặc định là 'active'
            if (staff.getStatus() == null || staff.getStatus().isEmpty()) {
                preparedStatement.setString(13, "active");
            } else {
                preparedStatement.setString(13, staff.getStatus());
            }

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

    // Phương thức xóa nhân viên theo ID chuyen active thaành inactive
    public boolean deleteStaff(int staffID) {
        String query = "UPDATE staff SET status = 'inactive' WHERE staffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, staffID);
            int rowsAffected = preparedStatement.executeUpdate();

            // Trả về true nếu cập nhật thành công (có ít nhất 1 hàng bị ảnh hưởng)
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về false nếu không cập nhật thành công
        return false;
    }

}
