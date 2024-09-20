package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Subject;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubjectModel {
    public List<Subject> selectName() {
        String sql = "SELECT SubjectID, SubjectName FROM subjects";
        List<Subject> subjectList = new ArrayList<>();

        try (Connection conn = ConnectDB.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("No data found in Subjects table.");
            }

            while (rs.next()) {
                int subjectID = rs.getInt("SubjectID");
                String subjectName = rs.getString("SubjectName");
                Subject subject = new Subject(subjectID, subjectName);
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subjectList;
    }
}
