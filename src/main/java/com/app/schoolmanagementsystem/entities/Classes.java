package com.app.schoolmanagementsystem.entities;

import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Classes {
    private int classID;
    private String className;
    private String section;
    private int staffID;
    private LocalDate enrollmentDate;
    private LocalDate completeDate;

    // Constructor
    public Classes(int classID, String className, String section, int staffID, LocalDate enrollmentDate, LocalDate completeDate) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
        this.enrollmentDate = enrollmentDate;
        this.completeDate = completeDate;
    }

    // Getters and Setters

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    // Save Class to Database
    public boolean saveClass(ClassModel newClass) {
        String sql = "INSERT INTO Classes (ClassName, Section, StaffID, EnrollmentDate, CompleteDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newClass.getClassName());
            stmt.setString(2, newClass.getSection());
            stmt.setInt(3, newClass.getStaffID());
            stmt.setDate(4, new Date(newClass.getEnrollmentDate().getTime()));
            stmt.setDate(5, new Date(newClass.getCompleteDate().getTime()));

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
