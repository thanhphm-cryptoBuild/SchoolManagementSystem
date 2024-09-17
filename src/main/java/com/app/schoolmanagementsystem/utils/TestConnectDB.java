package com.app.schoolmanagementsystem.utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnectDB {

    public static void main(String[] args) {
        // Kiểm tra kết nối cơ sở dữ liệu
        Connection conn = com.app.schoolmanagementsystem.utils.ConnectDB.connection();
        if (conn != null) {
            System.out.println("Kết nối cơ sở dữ liệu thành công!");

            try {
                // Tạo câu lệnh SQL
                Statement stmt = conn.createStatement();
                // Thực hiện truy vấn cơ bản
                ResultSet rs = stmt.executeQuery("SELECT DATABASE();");

                if (rs.next()) {
                    // In ra tên cơ sở dữ liệu hiện tại
                    System.out.println("Cơ sở dữ liệu hiện tại: " + rs.getString(1));
                }

                // Đóng ResultSet và Statement
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Ngắt kết nối sau khi kiểm tra xong
                ConnectDB.disconnect();
            }
        } else {
            System.out.println("Kết nối cơ sở dữ liệu không thành công.");
        }
    }
}