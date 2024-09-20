package com.app.schoolmanagementsystem.entities;

import com.app.schoolmanagementsystem.model.ClassModel;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.*;

public class Classes {
    public Classes(int classID, String className, String section, int staffID, java.time.LocalDate enrollmentDate, java.time.LocalDate completeDate) {

    }

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
}
