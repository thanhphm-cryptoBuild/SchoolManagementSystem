package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.Student;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedModel {
    private Staff staff;

    private Student student;
    private Connection connection;

    public AdvancedModel() {
        this.connection = ConnectDB.connection(); // Khởi tạo kết nối từ ConnectDB
    }


    // Lấy danh sách các Staff có trạng thái 'inactive'
    public List<Staff> getInactiveStaff() {
        List<Staff> inactiveStaffList = new ArrayList<>();
        String sql = "SELECT * FROM Staff WHERE status = 'inactive'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff(
                        rs.getInt("staffID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getByte("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("positionName"),
                        rs.getString("avatar"),
                        rs.getString("status")
                );
                inactiveStaffList.add(staff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inactiveStaffList;
    }

    // Lấy danh sách các Student có trạng thái 'inactive'
    public List<Student> getInactiveStudents() {
        List<Student> inactiveStudentList = new ArrayList<>();
        String sql = "SELECT * FROM Students WHERE status = 'inactive'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getByte("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("avatar"),
                        rs.getString("status")
                );
                inactiveStudentList.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inactiveStudentList;
    }

    // Phương thức khôi phục trạng thái active của Staff
    public boolean restoreStaffStatus(int staffID) {
        String updateQuery = "UPDATE staff SET status = 'active' WHERE staffID = ? AND status = 'inactive'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, staffID);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có bản ghi được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    // Phương thức khôi phục trạng thái active của Student
    public boolean restoreStudentStatus(int studentID) {
        String updateQuery = "UPDATE students SET status = 'active' WHERE studentID = ? AND status = 'inactive'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setInt(1, studentID);
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0; // Trả về true nếu có bản ghi được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public List<Staff> searchInactiveStaff(String firstName, String email, Byte gender, Integer id) {
        List<Staff> resultList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Staff WHERE status = 'inactive'");

        // Xây dựng truy vấn động cho firstName và email
        boolean hasNameOrEmailCondition = false;

        if (firstName != null && !firstName.isEmpty()) {
            sql.append(" AND firstName LIKE ?");
            hasNameOrEmailCondition = true;
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
            hasNameOrEmailCondition = true;
        }

        // Thêm các điều kiện bổ sung
        if (gender != null) {
            sql.append(" AND gender = ?");
        }
        if (id != null) {
            sql.append(" AND staffID = ?");
        }

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set giá trị cho các tham số truy vấn
            if (firstName != null && !firstName.isEmpty()) {
                statement.setString(paramIndex++, "%" + firstName + "%");
            }
            if (email != null && !email.isEmpty()) {
                statement.setString(paramIndex++, "%" + email + "%");
            }
            if (gender != null) {
                statement.setByte(paramIndex++, gender);
            }
            if (id != null) {
                statement.setInt(paramIndex++, id);
            }

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff(
                        rs.getInt("staffID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getByte("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("positionName"),
                        rs.getString("avatar"),
                        rs.getString("status")
                );
                resultList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xem xét ghi log hoặc thông báo lỗi
        }
        return resultList;
    }

    public List<Student> searchInactiveStudent(String firstName, String email, Byte gender, Integer id) {
        List<Student> resultList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Students WHERE status = 'inactive'");

        // Xây dựng truy vấn động cho firstName và email
        boolean hasNameOrEmailCondition = false;

        if (firstName != null && !firstName.isEmpty()) {
            sql.append(" AND firstName LIKE ?");
            hasNameOrEmailCondition = true;
        }
        if (email != null && !email.isEmpty()) {
            sql.append(" AND email LIKE ?");
            hasNameOrEmailCondition = true;
        }

        // Thêm các điều kiện bổ sung
        if (gender != null) {
            sql.append(" AND gender = ?");
        }
        if (id != null) {
            sql.append(" AND studentID = ?");
        }

        try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set giá trị cho các tham số truy vấn
            if (firstName != null && !firstName.isEmpty()) {
                statement.setString(paramIndex++, "%" + firstName + "%");
            }
            if (email != null && !email.isEmpty()) {
                statement.setString(paramIndex++, "%" + email + "%");
            }
            if (gender != null) {
                statement.setByte(paramIndex++, gender);
            }
            if (id != null) {
                statement.setInt(paramIndex++, id);
            }

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getByte("gender"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("avatar"),
                        rs.getString("status")
                );
                resultList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xem xét ghi log hoặc thông báo lỗi
        }
        return resultList;
    }


}
