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
    public List<Staff> selectName(){
        String sql = "SELECT StaffID, FirstName, LastName, PhoneNumber, PositionName FROM staff";
        List<Staff> staffList = new ArrayList<>();
        try (Connection conn = ConnectDB.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int staffID = rs.getInt("StaffID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String phoneNumber = rs.getString("PhoneNumber");
                String positionName = rs.getString("PositionName");
                Staff staff = new Staff(staffID, firstName,lastName,phoneNumber, positionName);
                staffList.add(staff);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }


}
