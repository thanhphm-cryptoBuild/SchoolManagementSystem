package com.app.schoolmanagementsystem.utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnectDB {

    public static void main(String[] args) {
        Connection conn = com.app.schoolmanagementsystem.utils.ConnectDB.connection();
        if (conn != null) {
            System.out.println("Connect Database Successfully!");

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT DATABASE();");

                if (rs.next()) {
                    System.out.println("Current Database: " + rs.getString(1));
                }

                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ConnectDB.disconnect();
            }
        } else {
            System.out.println("Connect Database Failed.");
        }
    }
}