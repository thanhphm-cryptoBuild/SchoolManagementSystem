package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.entities.SubjectClass;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubjectClassModel {

    public void addClassSubject(int classID, int subjectID, int staffID) {

        String sql = "INSERT INTO ClassSubjects (classID, subjectID, staffID) VALUES (?, ?, ?)";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, classID);
            preparedStatement.setInt(2, subjectID);
            preparedStatement.setInt(3, staffID);

            int rowsInserted = preparedStatement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new ClassSubject was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClassSubject(int classSubjectID) {
        String sql = "DELETE FROM ClassSubjects WHERE ClassSubjectID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, classSubjectID);

            int rowsDeleted = preparedStatement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("ClassSubject was deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateClassSubject(int classSubjectID, int classID, int subjectID, int staffID) {
        String sql = "UPDATE ClassSubjects SET classID = ?, subjectID = ?, staffID = ? WHERE classSubjectID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setInt(1, classID);
            preparedStatement.setInt(2, subjectID);
            preparedStatement.setInt(3, staffID);
            preparedStatement.setInt(4, classSubjectID);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("ClassSubject was updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isSubjectNameExists(int subjectID, int classID) {
        String sql = "SELECT COUNT(*) FROM ClassSubjects cs " +
                "WHERE cs.SubjectID = ? AND cs.ClassID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subjectID);
            stmt.setInt(2, classID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isSubjectNameEditExists(int subjectID, int classID, int classSubjectID) {
        String sql = "SELECT COUNT(*) FROM ClassSubjects cs " +
                "WHERE cs.SubjectID = ? AND cs.ClassID = ? AND cs.classSubjectID != ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, subjectID);
            stmt.setInt(2, classID);
            stmt.setInt(3, classSubjectID);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    public SubjectClass getClassSubjectByID(int classSubjectID) {
        SubjectClass subjectClass = null;
        String query = "SELECT c.ClassSubjectID, c.ClassID, c.SubjectID, c.StaffID, " +
                "       CONCAT(s.FirstName, ' ', s.LastName) AS TeacherName, " +
                "       s.PhoneNumber AS TeacherPhoneNumber, " +
                "       CONCAT(g.ClassName, g.Section, ' (', YEAR(g.EnrollmentDate), ' - ', YEAR(g.CompleteDate), ') ') AS ClassNameYear, " +
                "       h.SubjectName " +
                "FROM ClassSubjects c " +
                "JOIN Staff s ON c.StaffID = s.StaffID " +
                "JOIN Classes g ON c.ClassID = g.ClassID " +
                "JOIN Subjects h ON c.SubjectID = h.SubjectID " +
                "WHERE c.ClassSubjectID = ?";

        try (Connection connection = ConnectDB.connection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, classSubjectID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int classID = rs.getInt("classID");
                int subjectID = rs.getInt("subjectID");
                int staffID = rs.getInt("staffID");
                String teacherName = rs.getString("TeacherName");
                String teacherPhoneNumber = rs.getString("TeacherPhoneNumber");
                String classNameYear = rs.getString("ClassNameYear");
                String subjectName = rs.getString("SubjectName");

                subjectClass = new SubjectClass(
                        classSubjectID,
                        classID,
                        classNameYear,
                        subjectID,
                        subjectName,
                        staffID,
                        teacherName,
                        teacherPhoneNumber
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectClass;
    }


    public List<SubjectClass> getAllClassSubject() {
        List<SubjectClass> classesList = new ArrayList<>();
        String query = "SELECT c.classSubjectID, c.classID, c.subjectID, c.staffID, " +
                "       CONCAT(s.FirstName, ' ', s.LastName) AS TeacherName, " +
                "       s.PhoneNumber AS TeacherPhoneNumber, " +
                "       CONCAT(g.ClassName, g.Section, ' (', YEAR(g.EnrollmentDate), ' - ', YEAR(g.CompleteDate), ') ') AS ClassNameYear, " +
                "       h.SubjectName " +
                "FROM ClassSubjects c " +
                "JOIN Staff s ON c.staffID = s.staffID " +
                "JOIN Classes g ON c.classID = g.classID " +
                "JOIN Subjects h ON c.subjectID = h.subjectID";

        try (Connection connection = ConnectDB.connection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int classSubjectID = rs.getInt("classSubjectID");
                int classID = rs.getInt("classID");
                int subjectID = rs.getInt("subjectID");
                int staffID = rs.getInt("staffID");
                String teacherName = rs.getString("TeacherName");
                String teacherPhoneNumber = rs.getString("TeacherPhoneNumber");
                String classNameYear = rs.getString("ClassNameYear");
                String subjectName = rs.getString("SubjectName");

                SubjectClass sjc = new SubjectClass(
                        classSubjectID,
                        classID,
                        classNameYear,
                        subjectID,
                        subjectName,
                        staffID,
                        teacherName,
                        teacherPhoneNumber
                );

                classesList.add(sjc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classesList;
    }
}
