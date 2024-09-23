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
    public Classes(int i, String selectedClass, String section, int staffID, LocalDate enrollmentDate, LocalDate completeDate,  String description) {
    }

    // Constructor
//    public Classes(int classID, String className, String section, int staffID, LocalDate enrollmentDate, LocalDate completeDate) {
//        this.classID = classID;
//        this.className = className;
//        this.section = section;
//        this.staffID = staffID;
//        this.enrollmentDate = enrollmentDate;
//        this.completeDate = completeDate;
//    }

    // Getters and Setters

    public Classes() {
        //TODO Auto-generated constructor stub
    }

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
        String sql = "INSERT INTO Classes (ClassName, Section, StaffID, Description, EnrollmentDate, CompleteDate) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newClass.getClassName());
            stmt.setString(2, newClass.getSection());
            stmt.setInt(3, newClass.getStaffID());
            stmt.setDate(4, java.sql.Date.valueOf(newClass.getEnrollmentDate()));
            stmt.setDate(5, java.sql.Date.valueOf(newClass.getCompleteDate()));
            stmt.setString(4, newClass.getDescription());
            stmt.setDate(5, Date.valueOf(newClass.getEnrollmentDate()));
            stmt.setDate(6, Date.valueOf(newClass.getCompleteDate()));

            int rowsInserted = stmt.executeUpdate();

            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateClass(ClassModel updatedClass) {
        String sql = "UPDATE Classes SET ClassName = ?, Section = ?, StaffID = ?, Description = ?, EnrollmentDate = ?, CompleteDate = ? WHERE ClassID = ?";

        try (Connection conn = ConnectDB.connection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, updatedClass.getClassName());
            stmt.setString(2, updatedClass.getSection());
            stmt.setInt(3, updatedClass.getStaffID());
            stmt.setString(4, updatedClass.getDescription());
            stmt.setDate(5, Date.valueOf(updatedClass.getEnrollmentDate()));
            stmt.setDate(6, Date.valueOf(updatedClass.getCompleteDate()));
            stmt.setInt(7, updatedClass.getClassID());

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<ClassModel> getAllClasses() {
        List<ClassModel> classesList = new ArrayList<>();
        String query = "SELECT c.ClassID, c.ClassName, c.Section, c.StaffID, c.EnrollmentDate, c.CompleteDate, c.Description, " +
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
                        rs.getString("Description"),
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

    public boolean isStaffIDUnique(int staffID, int enrollmentYear, int completeYear) {
        String query = "SELECT COUNT(*) FROM Classes WHERE StaffID = ? AND YEAR(EnrollmentDate) = ? AND YEAR(CompleteDate) = ?";

        try (Connection connection = ConnectDB.connection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, staffID);
            pstmt.setInt(2, enrollmentYear);
            pstmt.setInt(3, completeYear);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAcademicYearValid(String className, String section, int newEnrollmentYear, int newCompleteYear) {
        String query = "SELECT COUNT(*) FROM Classes WHERE ClassName = ? AND Section = ? AND (" +
                "(YEAR(EnrollmentDate) <= ? AND YEAR(CompleteDate) > ?) OR " +
                "(YEAR(EnrollmentDate) < ? AND YEAR(CompleteDate) >= ?))";

        try (Connection connection = ConnectDB.connection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, className);
            pstmt.setString(2, section);
            pstmt.setInt(3, newEnrollmentYear);
            pstmt.setInt(4, newEnrollmentYear);
            pstmt.setInt(5, newCompleteYear);
            pstmt.setInt(6, newCompleteYear);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isStaffIDUniqueEdit(int staffID, int enrollmentYear, int completeYear, int currentClassID) {
        String query = "SELECT COUNT(*) FROM Classes WHERE StaffID = ? AND YEAR(EnrollmentDate) = ? AND YEAR(CompleteDate) = ? AND ClassID != ?";

        try (Connection connection = ConnectDB.connection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, staffID);
            pstmt.setInt(2, enrollmentYear);
            pstmt.setInt(3, completeYear);
            pstmt.setInt(4, currentClassID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isAcademicYearValidEdit(String className, String section, int newEnrollmentYear, int newCompleteYear, int currentClassID) {
        String query = "SELECT COUNT(*) FROM Classes WHERE ClassName = ? AND Section = ? AND (" +
                "(YEAR(EnrollmentDate) <= ? AND YEAR(CompleteDate) > ?) OR " +
                "(YEAR(EnrollmentDate) < ? AND YEAR(CompleteDate) >= ?)) AND ClassID != ?";

        try (Connection connection = ConnectDB.connection();
             PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, className);
            pstmt.setString(2, section);
            pstmt.setInt(3, newEnrollmentYear);
            pstmt.setInt(4, newEnrollmentYear);
            pstmt.setInt(5, newCompleteYear);
            pstmt.setInt(6, newCompleteYear);
            pstmt.setInt(7, currentClassID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
