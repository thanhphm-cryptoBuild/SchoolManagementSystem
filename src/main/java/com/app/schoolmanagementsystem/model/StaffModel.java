package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import com.app.schoolmanagementsystem.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class StaffModel {

    // Phương thức đếm số lượng nhân viên trạng thái active
    public int countActiveStaff() {
        int count = 0;
        String query = "SELECT COUNT(*) AS totalActiveStaff FROM Staffs WHERE Status = ?";

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

    // Phương thức lấy danh sách tất cả nhân viên trạng thái active
    public List<Staff> getActiveStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, PositionID, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed " +
                "FROM Staffs WHERE Status = 'active'";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Staff staff = new Staff(
                        resultSet.getInt("StaffID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getDate("DateOfBirth"),
                        resultSet.getByte("Gender"),
                        resultSet.getString("Address"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getDate("HireDate"),
                        resultSet.getInt("PositionID"),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("EducationBackground"),
                        resultSet.getString("Experience"),
                        resultSet.getString("Avatar"),
                        resultSet.getString("Status"),
                        resultSet.getString("ResetCode"),
                        resultSet.getTimestamp("ResetCodeCreationTime").toLocalDateTime(),
                        resultSet.getBoolean("IsResetCodeUsed")
                );
                staffList.add(staff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    // Phương thức thêm nhân viên với trạng thái mặc định là active
    public boolean addStaff(Staff staff) {
        String query = "INSERT INTO Staffs (FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, PositionID, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
            String hashedPassword = PasswordUtil.hashPassword(staff.getPassword());

            preparedStatement.setString(1, staff.getFirstName());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setDate(3, staff.getDateOfBirth());
            preparedStatement.setByte(4, staff.getGender());
            preparedStatement.setString(5, staff.getAddress());
            preparedStatement.setString(6, staff.getPhoneNumber());
            preparedStatement.setString(7, staff.getEmail());
            preparedStatement.setString(8, hashedPassword);  // Sử dụng mật khẩu đã mã hóa
            preparedStatement.setDate(9, staff.getHireDate());
            preparedStatement.setInt(10, staff.getPositionID());
            preparedStatement.setDouble(11, staff.getSalary());
            preparedStatement.setString(12, staff.getEducationBackground());
            preparedStatement.setString(13, staff.getExperience());
            preparedStatement.setString(14, staff.getAvatar());
            preparedStatement.setString(15, staff.getStatus() != null && !staff.getStatus().isEmpty() ? staff.getStatus() : "active");
            preparedStatement.setString(16, staff.getResetCode());
            preparedStatement.setTimestamp(17, Timestamp.valueOf(staff.getResetCodeCreationTime()));
            preparedStatement.setBoolean(18, staff.isResetCodeUsed());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức cập nhật thông tin nhân viên
    public boolean updateStaff(Staff staff) {
        String query = "UPDATE Staffs SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, Address = ?, PhoneNumber = ?, Email = ?, Password = ?, HireDate = ?, PositionID = ?, Salary = ?, EducationBackground = ?, Experience = ?, Avatar = ?, Status = ?, ResetCode = ?, ResetCodeCreationTime = ?, IsResetCodeUsed = ? WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            // Mã hóa mật khẩu nếu có sự thay đổi
            String password = staff.getPassword();
            if (password != null && !password.isEmpty()) {
                password = PasswordUtil.hashPassword(password);
            }

            preparedStatement.setString(1, staff.getFirstName());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setDate(3, staff.getDateOfBirth());
            preparedStatement.setByte(4, staff.getGender());
            preparedStatement.setString(5, staff.getAddress());
            preparedStatement.setString(6, staff.getPhoneNumber());
            preparedStatement.setString(7, staff.getEmail());
            preparedStatement.setString(8, password);  // Mã hóa mật khẩu nếu có thay đổi
            preparedStatement.setDate(9, staff.getHireDate());
            preparedStatement.setInt(10, staff.getPositionID());
            preparedStatement.setDouble(11, staff.getSalary());
            preparedStatement.setString(12, staff.getEducationBackground());
            preparedStatement.setString(13, staff.getExperience());
            preparedStatement.setString(14, staff.getAvatar());
            preparedStatement.setString(15, staff.getStatus());
            preparedStatement.setString(16, staff.getResetCode());
            preparedStatement.setTimestamp(17, Timestamp.valueOf(staff.getResetCodeCreationTime()));
            preparedStatement.setBoolean(18, staff.isResetCodeUsed());
            preparedStatement.setInt(19, staff.getStaffID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức xóa nhân viên theo ID chuyển trạng thái từ active thành inactive
    public boolean deleteStaff(int staffID) {
        String query = "UPDATE Staffs SET Status = 'inactive' WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, staffID);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
