package com.app.schoolmanagementsystem.entities;

import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Classes {

    public Classes(int i, String className, String section, int staffID, LocalDate enrollmentDate, LocalDate completeDate) {
    }

    public Classes() {

    }

    public boolean saveClass(ClassModel newClass) {
        String sql = "INSERT INTO Classes (ClassName, Section, StaffID, EnrollmentDate, CompleteDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newClass.getClassName());
            stmt.setString(2, newClass.getSection());
            stmt.setInt(3, newClass.getStaffID());
            stmt.setDate(4, Date.valueOf(newClass.getEnrollmentDate()));
            stmt.setDate(5, Date.valueOf(newClass.getCompleteDate()));

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<ClassModel> getAllClasses() {
        List<ClassModel> classesList = new ArrayList<>();
        String query = "SELECT c.ClassID, c.ClassName, c.Section, c.StaffID, c.EnrollmentDate, c.CompleteDate, " +
                "CONCAT(s.FirstName, ' ', s.LastName) AS TeacherName, s.PhoneNumber AS TeacherPhoneNumber " +
                "FROM Classes c " +
                "JOIN Staff s ON c.StaffID = s.StaffID";

        try (Connection connection = ConnectDB.connection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                ClassModel cls = new ClassModel(
                        rs.getInt("ClassID"),
                        rs.getString("ClassName"),
                        rs.getString("Section"),
                        rs.getInt("StaffID"),
                        rs.getDate("EnrollmentDate").toLocalDate(),
                        rs.getDate("CompleteDate").toLocalDate(),
                        rs.getString("TeacherName"),
                        rs.getString("TeacherPhoneNumber")
                );
                classesList.add(cls);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classesList;
    }
}
