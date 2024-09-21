package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Advanced {
    private Staff staff;

    private Student student;
    private Connection connection;


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
                        rs.getDate("dateOfBirth"),
                        rs.getByte("gender"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDate("hireDate"),
                        rs.getString("positionName"),
                        rs.getDouble("salary"),
                        rs.getString("educationBackground"),
                        rs.getString("experience"),
                        rs.getString("avatar"),
                        rs.getString("status"),
                        rs.getString("resetCode"),
                        rs.getTimestamp("resetCodeCreationTime").toLocalDateTime(),
                        rs.getBoolean("isResetCodeUsed")
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
        String sql = "SELECT * FROM Student WHERE status = 'inactive'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dateOfBirth"),
                        rs.getByte("gender"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getDate("enrollmentDate"),
                        rs.getInt("classID"),
                        rs.getString("avatar"),
                        rs.getString("previousSchool"),
                        rs.getString("reasonForLeaving"),
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

    public List<Object> searchInactivePersons(String fullName, String email, Integer id, String gender) {
        List<Object> result = new ArrayList<>();

        // Tìm kiếm trong Staff
        StringBuilder staffQuery = new StringBuilder("SELECT * FROM Staff WHERE status = 'inactive'");
        List<Object> staffParams = new ArrayList<>();

        if (fullName != null && !fullName.trim().isEmpty()) {
            staffQuery.append(" AND CONCAT(firstName, ' ', lastName) LIKE ?");
            staffParams.add("%" + fullName.trim() + "%");
        }

        if (email != null && !email.trim().isEmpty()) {
            staffQuery.append(" AND email LIKE ?");
            staffParams.add("%" + email.trim() + "%");
        }

        if (id != null) {
            staffQuery.append(" AND staffID = ?");
            staffParams.add(id);
        }

        if (gender != null && !gender.trim().isEmpty()) {
            staffQuery.append(" AND gender = ?");
            // Giả sử gender được lưu dưới dạng Byte: 1 cho Male, 0 cho Female, 2 cho Other
            Byte genderByte;
            switch (gender.toLowerCase()) {
                case "male":
                    genderByte = 1;
                    break;
                case "female":
                    genderByte = 0;
                    break;
                default:
                    genderByte = 2;
                    break;
            }
            staffParams.add(genderByte);
        }

        try (PreparedStatement staffStmt = connection.prepareStatement(staffQuery.toString())) {
            for (int i = 0; i < staffParams.size(); i++) {
                staffStmt.setObject(i + 1, staffParams.get(i));
            }

            ResultSet rs = staffStmt.executeQuery();
            while (rs.next()) {
                Staff staff = new Staff(
                        rs.getInt("staffID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dateOfBirth"),
                        rs.getByte("gender"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDate("hireDate"),
                        rs.getString("positionName"),
                        rs.getDouble("salary"),
                        rs.getString("educationBackground"),
                        rs.getString("experience"),
                        rs.getString("avatar"),
                        rs.getString("status"),
                        rs.getString("resetCode"),
                        rs.getTimestamp("resetCodeCreationTime") != null ? rs.getTimestamp("resetCodeCreationTime").toLocalDateTime() : null,
                        rs.getBoolean("isResetCodeUsed")
                );
                result.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tìm kiếm trong Student
        StringBuilder studentQuery = new StringBuilder("SELECT * FROM Students WHERE status = 'inactive'");
        List<Object> studentParams = new ArrayList<>();

        if (fullName != null && !fullName.trim().isEmpty()) {
            studentQuery.append(" AND CONCAT(firstName, ' ', lastName) LIKE ?");
            studentParams.add("%" + fullName.trim() + "%");
        }

        if (email != null && !email.trim().isEmpty()) {
            studentQuery.append(" AND email LIKE ?");
            studentParams.add("%" + email.trim() + "%");
        }

        if (id != null) {
            studentQuery.append(" AND studentID = ?");
            studentParams.add(id);
        }

        if (gender != null && !gender.trim().isEmpty()) {
            studentQuery.append(" AND gender = ?");
            Byte genderByte;
            switch (gender.toLowerCase()) {
                case "male":
                    genderByte = 1;
                    break;
                case "female":
                    genderByte = 0;
                    break;
                default:
                    genderByte = 2;
                    break;
            }
            studentParams.add(genderByte);
        }

        try (PreparedStatement studentStmt = connection.prepareStatement(studentQuery.toString())) {
            for (int i = 0; i < studentParams.size(); i++) {
                studentStmt.setObject(i + 1, studentParams.get(i));
            }

            ResultSet rs = studentStmt.executeQuery();
            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentID"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getDate("dateOfBirth"),
                        rs.getByte("gender"),
                        rs.getString("address"),
                        rs.getString("phoneNumber"),
                        rs.getString("email"),
                        rs.getDate("enrollmentDate"),
                        rs.getInt("classID"),
                        rs.getString("avatar"),
                        rs.getString("previousSchool"),
                        rs.getString("reasonForLeaving"),
                        rs.getString("status")
                );
                result.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }


}
