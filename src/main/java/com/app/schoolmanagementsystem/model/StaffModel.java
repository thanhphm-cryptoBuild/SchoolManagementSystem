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
    private Connection connection;

    public StaffModel(Staff staff, Connection connection) {
        this.staff = staff;
        this.connection = connection;
    }

    public StaffModel() {
    }

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

    public String getAvatarByStaffId(int staffId) {
        String avatarName = null;
        String query = "SELECT Avatar FROM Staff WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, staffId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                avatarName = rs.getString("Avatar");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return avatarName;
    }


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



    public boolean addStaff(Staff staff, List<StaffFamily> familyMembers, StaffRoles staffRole) {
        boolean isValid = true;

        if (!validateStaffData(staff)) {
            System.out.println("Dữ liệu nhân viên không hợp lệ.");
            isValid = false;
        } else {

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

            String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

            if (staff.getEmail() == null || staff.getEmail().isEmpty()) {
                System.out.println("Email không được trống.");
                isValid = false;
            } else if (!staff.getEmail().matches(emailRegex)) {
                System.out.println("Email không đúng định dạng.");
                isValid = false;
            } else {
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

            if (staff.getAvatar() == null || staff.getAvatar().isEmpty()) {
                System.out.println("Hình đại diện không được trống.");
                isValid = false;
            } else {
                List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");

                String avatar = staff.getAvatar();
                String fileExtension = avatar.substring(avatar.lastIndexOf('.') + 1).toLowerCase();

                if (!validFormats.contains(fileExtension)) {
                    System.out.println("Hình đại diện phải có định dạng: png, jpg, hoặc jpeg.");
                    isValid = false;
                }
            }
        }

        if (familyMembers == null || familyMembers.isEmpty()) {
            System.out.println("Vui lòng nhập ít nhất một thành viên gia đình.");
            isValid = false;
        } else {
            boolean validFamilyMemberFound = false;

            for (StaffFamily familyMember : familyMembers) {
                boolean isValidMember = true;

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

                if (isValidMember) {
                    validFamilyMemberFound = true;
                }
            }

            if (!validFamilyMemberFound) {
                System.out.println("Vui lòng nhập thông tin chính xác cho ít nhất một thành viên gia đình.");
                isValid = false;
            }
        }


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

            conn.setAutoCommit(false);

            String password = PasswordUtil.hashPassword(staff.getPassword());
            LocalDateTime resetCodeCreationTime = staff.getResetCodeCreationTime();

            staffStatement.setString(1, staff.getFirstName());
            staffStatement.setString(2, staff.getLastName());
            staffStatement.setDate(3, staff.getDateOfBirth());
            staffStatement.setByte(4, staff.getGender());
            staffStatement.setString(5, staff.getAddress());
            staffStatement.setString(6, staff.getPhoneNumber());
            staffStatement.setString(7, staff.getEmail());
            staffStatement.setString(8, password);
            staffStatement.setDate(9, staff.getHireDate());
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

            int rowsAffected = staffStatement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = staffStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int staffID = generatedKeys.getInt(1);

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

                        if (staffRole != null) {
                            staffRoleStatement.setInt(1, staffID);
                            staffRoleStatement.setString(2, staffRole.getRoleName());
                            staffRoleStatement.executeUpdate();
                        }

                        conn.commit();
                        return true;
                    }
                }
            }

            conn.rollback();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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

        return isValid;
    }

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

    public boolean updateStaff(Staff staff, List<StaffFamily> familyMembers, StaffRoles staffRole) {
        boolean isValid = true;

        if (!validateStaffData(staff)) {
            System.out.println("Dữ liệu nhân viên không hợp lệ.");
            isValid = false;
        } else {
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
            if (staff.getAvatar() == null || staff.getAvatar().isEmpty()) {
                System.out.println("Hình đại diện không được trống.");
                isValid = false;
            } else {
                List<String> validFormats = Arrays.asList("png", "jpg", "jpeg");

                String avatar = staff.getAvatar();
                String fileExtension = avatar.substring(avatar.lastIndexOf('.') + 1).toLowerCase();

                if (!validFormats.contains(fileExtension)) {
                    System.out.println("Hình đại diện phải có định dạng: png, jpg, hoặc jpeg.");
                    isValid = false;
                }
            }
        }

        if (familyMembers == null || familyMembers.isEmpty()) {
            System.out.println("Vui lòng nhập ít nhất một thành viên gia đình.");
            isValid = false;
        } else {
            boolean validFamilyMemberFound = false;

            for (StaffFamily familyMember : familyMembers) {
                boolean isValidMember = true;

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

                if (isValidMember) {
                    validFamilyMemberFound = true;
                }
            }

            if (!validFamilyMemberFound) {
                System.out.println("Vui lòng nhập thông tin chính xác cho ít nhất một thành viên gia đình.");
                isValid = false;
            }
        }

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

            conn.setAutoCommit(false);


            String password;
            if (staff.getPassword() != null && !staff.getPassword().trim().isEmpty()) {
                if (staff.getPassword().length() < 5) {
                    System.out.println("Mật khẩu phải có ít nhất 5 ký tự.");
                    return false;
                }
                password = PasswordUtil.hashPassword(staff.getPassword());
            } else {
                password = getOldPasswordFromDatabase(staff.getStaffID());
            }

            LocalDateTime resetCodeCreationTime = staff.getResetCodeCreationTime();

            staffStatement.setString(1, staff.getFirstName());
            staffStatement.setString(2, staff.getLastName());
            staffStatement.setDate(3, staff.getDateOfBirth());
            staffStatement.setByte(4, staff.getGender());
            staffStatement.setString(5, staff.getAddress());
            staffStatement.setString(6, staff.getPhoneNumber());
            staffStatement.setString(7, staff.getEmail());
            staffStatement.setString(8, password);
            staffStatement.setDate(9, staff.getHireDate());
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
            staffStatement.setInt(18, staff.getStaffID());
            staffStatement.executeUpdate();

            staffRoleStatement.setString(1, staffRole.getRoleName());
            staffRoleStatement.setInt(2, staff.getStaffID());
            staffRoleStatement.executeUpdate();

            deleteFamilyStatement.setInt(1, staff.getStaffID());
            deleteFamilyStatement.executeUpdate();

            for (StaffFamily familyMember : familyMembers) {
                familyStatement.setInt(1, staff.getStaffID());
                familyStatement.setString(2, familyMember.getFamilyMemberName());
                familyStatement.setString(3, familyMember.getRelationshipType());
                familyStatement.setString(4, familyMember.getContactNumber());
                familyStatement.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

        List<Object> parameters = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            query.append(" AND FirstName LIKE ?");
            parameters.add("%" + firstName + "%");
        }

        if (email != null && !email.isEmpty()) {
            query.append(" AND Email LIKE ?");
            parameters.add("%" + email + "%");
        }

        if (id != null) {
            query.append(" AND StaffID = ?");
            parameters.add(id);
        }

        if (gender != null) {
            query.append(" AND Gender = ?");
            parameters.add(gender);
        }

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query.toString())) {

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
            pstmt.setInt(1, staff.getStaffID());
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
                            rs.getInt("FamilyID"),
                            rs.getInt("StaffID"),
                            rs.getString("FamilyMemberName"),
                            rs.getString("RelationshipType"),
                            rs.getString("ContactNumber")
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
    
    public void updateImagePathInDatabase(Integer staffID, String imageName) {
        String sql = "UPDATE staff SET avatar = ? WHERE staffID = ?";
        try (Connection connection = ConnectDB.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, imageName);
            preparedStatement.setInt(2, staffID);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Image path updated successfully.");
            } else {
                System.out.println("Failed to update image path.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}