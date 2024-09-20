package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.StaffFamily;
import com.app.schoolmanagementsystem.entities.StaffRoles;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import com.app.schoolmanagementsystem.utils.PasswordUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffModel {

    private Staff staff;
    private Connection connection; // Thêm thuộc tính Connection

    // Constructor
    public StaffModel(Staff staff, Connection connection) {
        this.staff = staff; // Khởi tạo staff
        this.connection = connection; // Khởi tạo Connection
    }

    public StaffModel() {
    }

    // Phương thức tìm kiếm vai trò theo tên
    public StaffRoles findRoleByName(String roleName) {
        StaffRoles staffRole = null;
        String query = "SELECT * FROM staffroles WHERE RoleName = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, roleName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                staffRole = new StaffRoles(
                        rs.getInt("StaffRoleID"),
                        rs.getString("RoleName")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffRole;
    }


    // Phương thức đếm số lượng nhân viên trạng thái active
    public int countActiveStaff() {
        int count = 0;
        String query = "SELECT COUNT(*) AS totalActiveStaff FROM Staff WHERE Status = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, "active");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt("totalActiveStaff");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    // Phương thức tìm vai trò theo tên


    // Phương thức lấy danh sách tất cả nhân viên trạng thái active
    public List<Staff> getActiveStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, PositionName, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed " +
                "FROM Staff WHERE Status = 'active'";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Staff staff = new Staff(
                        resultSet.getInt("StaffID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getDate("DateOfBirth"),
                        resultSet.getByte("Gender"),
                        resultSet.getString("Address"),
                        resultSet.getString("PhoneNumber"),
                        resultSet.getString("Email"),
                        resultSet.getString("Password"),
                        resultSet.getDate("HireDate"),
                        resultSet.getString("PositionName"),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("EducationBackground"),
                        resultSet.getString("Experience"),
                        resultSet.getString("Avatar"),
                        resultSet.getString("Status"),
                        resultSet.getString("ResetCode"),
                        resultSet.getTimestamp("ResetCodeCreationTime").toLocalDateTime(),
                        resultSet.getBoolean("IsResetCodeUsed")
                );
                staffList.add(staff);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    // Phương thức để lấy vai trò của nhân viên dựa trên StaffID
    public String getRoleByStaffID(int staffID) {
        String roleName = "";
        String query = "SELECT RoleName FROM StaffRoles WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, staffID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                roleName = rs.getString("RoleName");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleName;
    }



    // Phương thức thêm nhân viên mới
    public boolean addStaff(Staff staff, List<StaffFamily> familyMembers, StaffRoles staffRole) {
        boolean isValid = true;

        // Kiểm tra dữ liệu nhân viên
        if (!validateStaffData(staff)) {
            System.out.println("Dữ liệu nhân viên không hợp lệ.");
            isValid = false;
        } else {

            // Kiểm tra từng trường cụ thể của nhân viên
            if (staff.getFirstName() == null || staff.getFirstName().isEmpty()) {
                System.out.println("Tên nhân viên không được trống.");
                isValid = false;
            } else if (!Character.isUpperCase(staff.getFirstName().charAt(0))) {
                System.out.println("Tên nhân viên phải bắt đầu bằng chữ hoa.");
                isValid = false;
            }

            if (staff.getLastName() == null || staff.getLastName().isEmpty()) {
                System.out.println("Họ nhân viên không được trống.");
                isValid = false;
            } else if (!Character.isUpperCase(staff.getLastName().charAt(0))) {
                System.out.println("Họ nhân viên phải bắt đầu bằng chữ hoa.");
                isValid = false;
            }

            if (staff.getDateOfBirth() == null) {
                System.out.println("Ngày sinh không được trống.");
                isValid = false;
            } else {
                LocalDate dateOfBirth = staff.getDateOfBirth().toLocalDate();
                LocalDate limitDate = LocalDate.of(2000, 1, 1);
                if (dateOfBirth.isAfter(limitDate)) {
                    System.out.println("Ngày sinh phải trước năm 2000.");
                    isValid = false;
                }
            }
            if (staff.getGender() == null) {
                System.out.println("Giới tính không được trống.");
                isValid = false;
            }
            if (staff.getAddress() == null || staff.getAddress().isEmpty()) {
                System.out.println("Địa chỉ không được trống.");
                isValid = false;
            }
            if (staff.getPhoneNumber() == null || staff.getPhoneNumber().isEmpty() || !staff.getPhoneNumber().matches("\\d{10}")) {
                if (staff.getPhoneNumber() == null || staff.getPhoneNumber().isEmpty()) {
                    System.out.println("Số điện thoại không được trống.");
                } else if (!staff.getPhoneNumber().matches("\\d{10}")) {
                    System.out.println("Số điện thoại phải gồm 10 chữ số.");
                }
                isValid = false;
            }

            // Biểu thức chính quy để kiểm tra định dạng email
            // Biểu thức chính quy để kiểm tra định dạng email
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (staff.getEmail() == null || staff.getEmail().isEmpty()) {
                System.out.println("Email không được trống.");
                isValid = false;
            } else if (!staff.getEmail().matches(emailRegex)) {
                System.out.println("Email không đúng định dạng.");
                isValid = false;
            } else {
                // Kiểm tra email tồn tại trong CSDL và loại trừ staff hiện tại
                try (Connection conn = ConnectDB.connection()) {
                    if (isEmailExists(staff.getEmail(), staff.getStaffID(), conn)) {
                        System.out.println("Email đã tồn tại trong cơ sở dữ liệu.");
                        isValid = false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    isValid = false;
                }
            }


            if (staff.getPassword() == null || staff.getPassword().isEmpty()) {
                System.out.println("Mật khẩu không được trống.");
                isValid = false;
            } else if (staff.getPassword().length() < 5) {
                System.out.println("Mật khẩu phải có ít nhất 5 ký tự.");
                isValid = false;
            }

            if (staff.getHireDate() == null) {
                System.out.println("Ngày tuyển dụng không được trống.");
                isValid = false;
            }

            // Lấy hoặc thêm vị trí và nhận PositionName
            if (staff.getPositionName() == null || staff.getPositionName().isEmpty()) {
                System.out.println("Vị trí không hợp lệ.");
                isValid = false;
            }

            if (staff.getSalary() <= 0) {
                System.out.println("Lương không hợp lệ.");
                isValid = false;
            }

            if (staff.getEducationBackground() == null || staff.getEducationBackground().isEmpty()) {
                System.out.println("Trình độ học vấn không được trống.");
                isValid = false;
            }

            if (staff.getExperience() == null || staff.getExperience().isEmpty()) {
                System.out.println("Kinh nghiệm làm việc không được trống.");
                isValid = false;
            }
            // Kiểm tra định dạng của hình đại diện
            if (staff.getAvatar() == null || staff.getAvatar().isEmpty()) {
                System.out.println("Hình đại diện không được trống.");
                isValid = false;
            } else {
                // Danh sách các định dạng hợp lệ
                List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");

                // Lấy phần mở rộng của tệp
                String avatar = staff.getAvatar();
                String fileExtension = avatar.substring(avatar.lastIndexOf('.') + 1).toLowerCase();

                // Kiểm tra định dạng
                if (!validFormats.contains(fileExtension)) {
                    System.out.println("Hình đại diện phải có định dạng: png, jpg, hoặc jpeg.");
                    isValid = false;
                }
            }
        }

        // Kiểm tra danh sách thành viên gia đình
        if (familyMembers == null || familyMembers.isEmpty()) {
            System.out.println("Vui lòng nhập ít nhất một thành viên gia đình.");
            isValid = false;
        } else {
            boolean validFamilyMemberFound = false;

            for (StaffFamily familyMember : familyMembers) {
                boolean isValidMember = true;

                // Kiểm tra từng trường của thành viên gia đình
                if (familyMember.getFamilyMemberName() == null || familyMember.getFamilyMemberName().isEmpty()) {
                    System.out.println("Tên thành viên gia đình không được để trống.");
                    isValidMember = false;
                } else if (!familyMember.getFamilyMemberName().matches("[a-zA-Z\\s]+")) {
                    System.out.println("Tên thành viên gia đình phải là chữ.");
                    isValidMember = false;
                }

                if (familyMember.getRelationshipType() == null || familyMember.getRelationshipType().isEmpty()) {
                    System.out.println("Mối quan hệ của thành viên gia đình không được trống.");
                    isValidMember = false;
                }

                if (familyMember.getContactNumber() == null || !familyMember.getContactNumber().matches("\\d{10}")) {
                    System.out.println("Số điện thoại của thành viên gia đình phải là 10 chữ số.");
                    isValidMember = false;
                }

                // Nếu thông tin của thành viên này hợp lệ
                if (isValidMember) {
                    validFamilyMemberFound = true;
                }
            }

            // Nếu không có bất kỳ thành viên hợp lệ nào
            if (!validFamilyMemberFound) {
                System.out.println("Vui lòng nhập thông tin chính xác cho ít nhất một thành viên gia đình.");
                isValid = false;
            }
        }

        // Kiểm tra email tồn tại và loại trừ staffID hiện tại
//        try (Connection conn = ConnectDB.connection()) {
//            if (isEmailExists(staff.getEmail(), staff.getStaffID(), conn)) {
//                System.out.println("Email đã tồn tại trong cơ sở dữ liệu.");
//                isValid = false;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            isValid = false;
//        }

        if (!isValid) {
            return false;
        }


        String insertStaffQuery = "INSERT INTO Staff (FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, PositionName, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'active', ?, ?, ?)";

        String insertStaffRoleQuery = "INSERT INTO StaffRoles (StaffID, RoleName) VALUES (?, ?)";

        String insertFamilyMemberQuery = "INSERT INTO StaffFamily (StaffID, FamilyMemberName, RelationshipType, ContactNumber) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement staffStatement = conn.prepareStatement(insertStaffQuery, PreparedStatement.RETURN_GENERATED_KEYS);

             PreparedStatement staffRoleStatement = conn.prepareStatement(insertStaffRoleQuery);

             PreparedStatement familyStatement = conn.prepareStatement(insertFamilyMemberQuery)) {

            conn.setAutoCommit(false); // Bắt đầu giao dịch


            // Mã hóa mật khẩu
            String password = PasswordUtil.hashPassword(staff.getPassword());
            LocalDateTime resetCodeCreationTime = staff.getResetCodeCreationTime();

            // Thiết lập tham số cho PreparedStatement
            staffStatement.setString(1, staff.getFirstName());
            staffStatement.setString(2, staff.getLastName());
            staffStatement.setDate(3, staff.getDateOfBirth()); // Chuyển đổi LocalDate thành Date
            staffStatement.setByte(4, staff.getGender());
            staffStatement.setString(5, staff.getAddress());
            staffStatement.setString(6, staff.getPhoneNumber());
            staffStatement.setString(7, staff.getEmail());
            staffStatement.setString(8, password); // Mã hóa mật khẩu
            staffStatement.setDate(9, staff.getHireDate()); // Chuyển đổi LocalDate thành Date
            staffStatement.setString(10, staff.getPositionName());
            staffStatement.setDouble(11, staff.getSalary());
            staffStatement.setString(12, staff.getEducationBackground());
            staffStatement.setString(13, staff.getExperience());
            staffStatement.setString(14, staff.getAvatar());
            staffStatement.setString(15, staff.getResetCode());

            if (resetCodeCreationTime != null) {
                staffStatement.setTimestamp(16, Timestamp.valueOf(resetCodeCreationTime));
            } else {
                staffStatement.setNull(16, java.sql.Types.TIMESTAMP);
            }
            staffStatement.setBoolean(17, staff.isResetCodeUsed());

            // Thực thi câu lệnh SQL để thêm nhân viên
            int rowsAffected = staffStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = staffStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int staffID = generatedKeys.getInt(1);

                        // Xử lý bảng StaffFamily nếu có dữ liệu
                        if (familyMembers != null && !familyMembers.isEmpty()) {
                            for (StaffFamily familyMember : familyMembers) {
                                familyStatement.setInt(1, staffID);
                                familyStatement.setString(2, familyMember.getFamilyMemberName());
                                familyStatement.setString(3, familyMember.getRelationshipType());
                                familyStatement.setString(4, familyMember.getContactNumber());
                                familyStatement.addBatch();
                            }
                            familyStatement.executeBatch();
                        }

                        // Xử lý bảng StaffRoles
                        if (staffRole != null) {
                            staffRoleStatement.setInt(1, staffID);
                            staffRoleStatement.setString(2, staffRole.getRoleName());
                            staffRoleStatement.executeUpdate();
                        }

                        conn.commit(); // Xác nhận giao dịch
                        return true;
                    }
                }
            }

            conn.rollback(); // Hoàn tác giao dịch nếu có lỗi xảy ra

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }



    // Kiểm tra dữ liệu đầu vào
    private boolean validateStaffData(Staff staff) {
        boolean isValid = true;

        if (staff.getFirstName() == null || staff.getFirstName().trim().isEmpty()) {
            System.out.println("Tên không được để trống.");
            isValid = false;
        }

        if (staff.getLastName() == null || staff.getLastName().trim().isEmpty()) {
            System.out.println("Họ không được để trống.");
            isValid = false;
        }

        if (staff.getEmail() == null || staff.getEmail().trim().isEmpty()) {
            System.out.println("Email không được để trống.");
            isValid = false;
        }

        if (staff.getHireDate() == null) {
            System.out.println("getHireDate không được để trống.");
            isValid = false;
        }

//        if (staff.getPassword() == null || staff.getPassword().length() < 5) {
//            System.out.println("Mật khẩu phải có ít nhất 5 ký tự.");
//            isValid = false;
//        }

        return isValid;
    }

    // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
    private boolean isEmailExists(String email, int currentStaffID, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM Staff WHERE Email = ? AND StaffID != ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ps.setInt(2, currentStaffID);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }



    // Phương thức cập nhật nhân viên
    public boolean updateStaff(Staff staff, List<StaffFamily> familyMembers, StaffRoles staffRole) {
        boolean isValid = true;

        // Kiểm tra dữ liệu nhân viên (cùng logic như addStaff)
        if (!validateStaffData(staff)) {
            System.out.println("Dữ liệu nhân viên không hợp lệ.");
            isValid = false;
        } else {
            // Kiểm tra từng trường cụ thể của nhân viên (giống như addStaff)
            if (staff.getFirstName() == null || staff.getFirstName().isEmpty()) {
                System.out.println("Tên nhân viên không được trống.");
                isValid = false;
            } else if (!Character.isUpperCase(staff.getFirstName().charAt(0))) {
                System.out.println("Tên nhân viên phải bắt đầu bằng chữ hoa.");
                isValid = false;
            }

            if (staff.getLastName() == null || staff.getLastName().isEmpty()) {
                System.out.println("Họ nhân viên không được trống.");
                isValid = false;
            } else if (!Character.isUpperCase(staff.getLastName().charAt(0))) {
                System.out.println("Họ nhân viên phải bắt đầu bằng chữ hoa.");
                isValid = false;
            }

            if (staff.getDateOfBirth() == null) {
                System.out.println("Ngày sinh không được trống.");
                isValid = false;
            } else {
                LocalDate dateOfBirth = staff.getDateOfBirth().toLocalDate();
                LocalDate limitDate = LocalDate.of(2000, 1, 1);
                if (dateOfBirth.isAfter(limitDate)) {
                    System.out.println("Ngày sinh phải trước năm 2000.");
                    isValid = false;
                }
            }
            if (staff.getGender() == null) {
                System.out.println("Giới tính không được trống.");
                isValid = false;
            }
            if (staff.getAddress() == null || staff.getAddress().isEmpty()) {
                System.out.println("Địa chỉ không được trống.");
                isValid = false;
            }
            if (staff.getPhoneNumber() == null || staff.getPhoneNumber().isEmpty() || !staff.getPhoneNumber().matches("\\d{10}")) {
                if (staff.getPhoneNumber() == null || staff.getPhoneNumber().isEmpty()) {
                    System.out.println("Số điện thoại không được trống.");
                } else if (!staff.getPhoneNumber().matches("\\d{10}")) {
                    System.out.println("Số điện thoại phải gồm 10 chữ số.");
                }
                isValid = false;
            }

            // Biểu thức chính quy để kiểm tra định dạng email
            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (staff.getEmail() == null || staff.getEmail().isEmpty()) {
                System.out.println("Email không được trống.");
                isValid = false;
            } else if (!staff.getEmail().matches(emailRegex)) {
                System.out.println("Email không đúng định dạng.");
                isValid = false;
            }


            if (staff.getHireDate() == null) {
                System.out.println("Ngày tuyển dụng không được trống.");
                isValid = false;
            }

            // Lấy hoặc thêm vị trí và nhận PositionID
            if (staff.getPositionName() == null || staff.getPositionName().isEmpty()) {
                System.out.println("Vị trí không hợp lệ.");
                isValid = false;
            }

            if (staff.getSalary() <= 0) {
                System.out.println("Lương không hợp lệ.");
                isValid = false;
            }

            if (staff.getEducationBackground() == null || staff.getEducationBackground().isEmpty()) {
                System.out.println("Trình độ học vấn không được trống.");
                isValid = false;
            }

            if (staff.getExperience() == null || staff.getExperience().isEmpty()) {
                System.out.println("Kinh nghiệm làm việc không được trống.");
                isValid = false;
            }
            // Kiểm tra định dạng của hình đại diện
            if (staff.getAvatar() == null || staff.getAvatar().isEmpty()) {
                System.out.println("Hình đại diện không được trống.");
                isValid = false;
            } else {
                // Danh sách các định dạng hợp lệ
                List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");

                // Lấy phần mở rộng của tệp
                String avatar = staff.getAvatar();
                String fileExtension = avatar.substring(avatar.lastIndexOf('.') + 1).toLowerCase();

                // Kiểm tra định dạng
                if (!validFormats.contains(fileExtension)) {
                    System.out.println("Hình đại diện phải có định dạng: png, jpg, hoặc jpeg.");
                    isValid = false;
                }
            }
        }

        // Kiểm tra danh sách thành viên gia đình
        if (familyMembers == null || familyMembers.isEmpty()) {
            System.out.println("Vui lòng nhập ít nhất một thành viên gia đình.");
            isValid = false;
        } else {
            boolean validFamilyMemberFound = false;

            for (StaffFamily familyMember : familyMembers) {
                boolean isValidMember = true;

                // Kiểm tra từng trường của thành viên gia đình
                if (familyMember.getFamilyMemberName() == null || familyMember.getFamilyMemberName().isEmpty()) {
                    System.out.println("Tên thành viên gia đình không được để trống.");
                    isValidMember = false;
                } else if (!familyMember.getFamilyMemberName().matches("[a-zA-Z\\s]+")) {
                    System.out.println("Tên thành viên gia đình phải là chữ.");
                    isValidMember = false;
                }

                if (familyMember.getRelationshipType() == null || familyMember.getRelationshipType().isEmpty()) {
                    System.out.println("Mối quan hệ của thành viên gia đình không được trống.");
                    isValidMember = false;
                }

                if (familyMember.getContactNumber() == null || !familyMember.getContactNumber().matches("\\d{10}")) {
                    System.out.println("Số điện thoại của thành viên gia đình phải là 10 chữ số.");
                    isValidMember = false;
                }

                // Nếu thông tin của thành viên này hợp lệ
                if (isValidMember) {
                    validFamilyMemberFound = true;
                }
            }

            // Nếu không có bất kỳ thành viên hợp lệ nào
            if (!validFamilyMemberFound) {
                System.out.println("Vui lòng nhập thông tin chính xác cho ít nhất một thành viên gia đình.");
                isValid = false;
            }
        }

        // Kiểm tra email tồn tại và loại trừ staffID hiện tại
        try (Connection conn = ConnectDB.connection()) {
            if (isEmailExists(staff.getEmail(), staff.getStaffID(), conn)) {
                System.out.println("Email đã tồn tại trong cơ sở dữ liệu.");
                isValid = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            isValid = false;
        }

        if (!isValid) {
            return false;
        }

        String updateStaffQuery = "UPDATE Staff SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, Address = ?, PhoneNumber = ?, Email = ?, Password = ?, HireDate = ?, PositionName = ?, Salary = ?, EducationBackground = ?, Experience = ?, Avatar = ?, Status = 'active', ResetCode = ?, ResetCodeCreationTime = ?, IsResetCodeUsed = ? WHERE StaffID = ?";
        String updateStaffRoleQuery = "UPDATE StaffRoles SET RoleName = ? WHERE StaffID = ?";
        String deleteFamilyMembersQuery = "DELETE FROM StaffFamily WHERE StaffID = ?";
        String insertFamilyMemberQuery = "INSERT INTO StaffFamily (StaffID, FamilyMemberName, RelationshipType, ContactNumber) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement staffStatement = conn.prepareStatement(updateStaffQuery);
             PreparedStatement staffRoleStatement = conn.prepareStatement(updateStaffRoleQuery);
             PreparedStatement deleteFamilyStatement = conn.prepareStatement(deleteFamilyMembersQuery);
             PreparedStatement familyStatement = conn.prepareStatement(insertFamilyMemberQuery)) {

            conn.setAutoCommit(false); // Bắt đầu giao dịch


            // Kiểm tra và mã hóa mật khẩu mới nếu được cung cấp
            String password;
            if (staff.getPassword() != null && !staff.getPassword().trim().isEmpty()) {
                // Nếu mật khẩu mới được cung cấp, kiểm tra độ dài
                if (staff.getPassword().length() < 5) {
                    System.out.println("Mật khẩu phải có ít nhất 5 ký tự.");
                    return false; // Hoặc bạn có thể thực hiện xử lý lỗi tại đây
                }
                // Mã hóa mật khẩu mới
                password = PasswordUtil.hashPassword(staff.getPassword());
            } else {
                // Nếu không có mật khẩu mới, giữ lại mật khẩu cũ từ cơ sở dữ liệu
                password = getOldPasswordFromDatabase(staff.getStaffID());
            }

            LocalDateTime resetCodeCreationTime = staff.getResetCodeCreationTime();

            // Thiết lập tham số cho PreparedStatement
            staffStatement.setString(1, staff.getFirstName());
            staffStatement.setString(2, staff.getLastName());
            staffStatement.setDate(3, staff.getDateOfBirth()); // Chuyển đổi LocalDate thành Date
            staffStatement.setByte(4, staff.getGender());
            staffStatement.setString(5, staff.getAddress());
            staffStatement.setString(6, staff.getPhoneNumber());
            staffStatement.setString(7, staff.getEmail());
            staffStatement.setString(8, password);
            staffStatement.setDate(9, staff.getHireDate()); // Chuyển đổi LocalDate thành Date
            staffStatement.setString(10, staff.getPositionName());
            staffStatement.setDouble(11, staff.getSalary());
            staffStatement.setString(12, staff.getEducationBackground());
            staffStatement.setString(13, staff.getExperience());
            staffStatement.setString(14, staff.getAvatar());
            staffStatement.setString(15, staff.getResetCode());

            if (resetCodeCreationTime != null) {
                staffStatement.setTimestamp(16, Timestamp.valueOf(resetCodeCreationTime));
            } else {
                staffStatement.setNull(16, Types.TIMESTAMP);
            }

            staffStatement.setBoolean(17, staff.isResetCodeUsed());
            staffStatement.setInt(18, staff.getStaffID()); // Thêm StaffID vào điều kiện WHERE
            staffStatement.executeUpdate();

            // Cập nhật vai trò
            staffRoleStatement.setString(1, staffRole.getRoleName());
            staffRoleStatement.setInt(2, staff.getStaffID());
            staffRoleStatement.executeUpdate();

            // Xóa các thành viên gia đình cũ
            deleteFamilyStatement.setInt(1, staff.getStaffID());
            deleteFamilyStatement.executeUpdate();

            // Thêm thành viên gia đình mới
            for (StaffFamily familyMember : familyMembers) {
                familyStatement.setInt(1, staff.getStaffID());
                familyStatement.setString(2, familyMember.getFamilyMemberName());
                familyStatement.setString(3, familyMember.getRelationshipType());
                familyStatement.setString(4, familyMember.getContactNumber());
                familyStatement.executeUpdate();
            }

            conn.commit(); // Commit nếu mọi thứ thành công
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức để lấy mật khẩu cũ từ cơ sở dữ liệu
    private String getOldPasswordFromDatabase(int staffID) {
        String currentPassword = null;
        String query = "SELECT Password FROM Staff WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    currentPassword = rs.getString("Password");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return currentPassword;
    }



    // Phương thức xóa nhân viên (thay vì xóa thực tế, cập nhật trạng thái)
    public boolean deleteStaff(int staffID) {
        String query = "UPDATE Staff SET Status = 'inactive' WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, staffID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức lấy thông tin nhân viên theo ID
    public Staff getStaffByID(int staffID) {
        Staff staff = null;
        String query = "SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, PositionName, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed " +
                "FROM Staff WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, staffID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    staff = new Staff(
                            resultSet.getInt("StaffID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getDate("DateOfBirth"),
                            resultSet.getByte("Gender"),
                            resultSet.getString("Address"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getDate("HireDate"),
                            resultSet.getString("PositionName"),
                            resultSet.getDouble("Salary"),
                            resultSet.getString("EducationBackground"),
                            resultSet.getString("Experience"),
                            resultSet.getString("Avatar"),
                            resultSet.getString("Status"),
                            resultSet.getString("ResetCode"),
                            resultSet.getTimestamp("ResetCodeCreationTime") != null ? resultSet.getTimestamp("ResetCodeCreationTime").toLocalDateTime() : null,
                            resultSet.getBoolean("IsResetCodeUsed")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }

    public List<Staff> searchStaff(String firstName, String email, Byte gender, Integer id) {
        List<Staff> staffList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, PositionName, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed FROM Staff WHERE Status = 'active'");

        // Danh sách tham số cho PreparedStatement
        List<Object> parameters = new ArrayList<>();

        // Thêm điều kiện tìm kiếm cho firstName và email
        if (firstName != null && !firstName.isEmpty()) {
            query.append(" AND FirstName LIKE ?");
            parameters.add("%" + firstName + "%");
        }

        if (email != null && !email.isEmpty()) {
            query.append(" AND Email LIKE ?");
            parameters.add("%" + email + "%");
        }

        // Thêm điều kiện phụ cho ID nếu có
        if (id != null) {
            query.append(" AND StaffID = ?");
            parameters.add(id);
        }

        // Thêm điều kiện phụ cho gender nếu có
        if (gender != null) {
            query.append(" AND Gender = ?");
            parameters.add(gender);
        }

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query.toString())) {

            // Thiết lập giá trị cho các tham số truy vấn
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Staff staff = new Staff(
                            resultSet.getInt("StaffID"),
                            resultSet.getString("FirstName"),
                            resultSet.getString("LastName"),
                            resultSet.getDate("DateOfBirth"),
                            resultSet.getByte("Gender"),
                            resultSet.getString("Address"),
                            resultSet.getString("PhoneNumber"),
                            resultSet.getString("Email"),
                            resultSet.getString("Password"),
                            resultSet.getDate("HireDate"),
                            resultSet.getString("PositionName"),
                            resultSet.getDouble("Salary"),
                            resultSet.getString("EducationBackground"),
                            resultSet.getString("Experience"),
                            resultSet.getString("Avatar"),
                            resultSet.getString("Status"),
                            resultSet.getString("ResetCode"),
                            resultSet.getTimestamp("ResetCodeCreationTime") != null ? resultSet.getTimestamp("ResetCodeCreationTime").toLocalDateTime() : null,
                            resultSet.getBoolean("IsResetCodeUsed")
                    );
                    staffList.add(staff);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    public String getRoleName() {
        String roleName = null;
        String query = "SELECT RoleName FROM StaffRoles WHERE StaffID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, staff.getStaffID()); // Sử dụng ID của Staff
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    roleName = rs.getString("RoleName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleName;
    }

    public List<StaffFamily> getFamilyMembers(int staffID) {
        List<StaffFamily> familyMembers = new ArrayList<>();
        String query = "SELECT * FROM StaffFamily WHERE StaffID = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, staffID);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    StaffFamily member = new StaffFamily(
                            rs.getInt("FamilyID"),                  // familyID
                            rs.getInt("StaffID"),                   // staffID
                            rs.getString("FamilyMemberName"),       // familyMemberName
                            rs.getString("RelationshipType"),       // relationshipType
                            rs.getString("ContactNumber")           // contactNumber
                    );
                    familyMembers.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return familyMembers;
    }


    public List<Staff> selectName(){
        String sql = "SELECT StaffID, FirstName, LastName, PhoneNumber, PositionName FROM staff";
        List<Staff> staffList = new ArrayList<>();
        try (Connection conn = ConnectDB.connection()){
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