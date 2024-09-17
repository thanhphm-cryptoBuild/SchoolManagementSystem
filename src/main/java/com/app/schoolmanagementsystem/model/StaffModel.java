package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.entities.Staff;
import com.app.schoolmanagementsystem.entities.StaffFamily;
import com.app.schoolmanagementsystem.entities.StaffRoles;
import com.app.schoolmanagementsystem.utils.ConnectDB;
import com.app.schoolmanagementsystem.utils.PasswordUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StaffModel {

    // Phương thức đếm số lượng nhân viên trạng thái active
    public int countActiveStaff() {
        int count = 0;
        String query = "SELECT COUNT(*) AS totalActiveStaff FROM Staffs WHERE Status = ?";

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

    // Phương thức lấy danh sách tất cả nhân viên trạng thái active
    public List<Staff> getActiveStaff() {
        List<Staff> staffList = new ArrayList<>();
        String query = "SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed " +
                "FROM Staffs WHERE Status = 'active'";

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
                LocalDate limitDate = LocalDate.of(2010, 1, 1);
                if (dateOfBirth.isAfter(limitDate)) {
                    System.out.println("Ngày sinh phải trước năm 2010.");
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

        if (!isValid) {
            return false;
        }

        String insertStaffQuery = "INSERT INTO Staffs (FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'active', ?, ?, ?)";

        String insertStaffRoleQuery = "INSERT INTO StaffRoles (StaffID, RoleName) VALUES (?, ?)";

        String insertFamilyMemberQuery = "INSERT INTO StaffFamily (StaffID, FamilyMemberName, RelationshipType, ContactNumber) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement staffStatement = conn.prepareStatement(insertStaffQuery, PreparedStatement.RETURN_GENERATED_KEYS);

             PreparedStatement staffRoleStatement = conn.prepareStatement(insertStaffRoleQuery);

             PreparedStatement familyStatement = conn.prepareStatement(insertFamilyMemberQuery)) {

            conn.setAutoCommit(false); // Bắt đầu giao dịch

            // Kiểm tra email tồn tại
            if (isEmailExists(staff.getEmail(), conn)) {
                System.out.println("Email đã tồn tại trong cơ sở dữ liệu.");
                return false;
            }

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
            staffStatement.setDouble(10, staff.getSalary());
            staffStatement.setString(11, staff.getEducationBackground());
            staffStatement.setString(12, staff.getExperience());
            staffStatement.setString(13, staff.getAvatar());
            staffStatement.setString(14, staff.getResetCode());

            if (resetCodeCreationTime != null) {
                staffStatement.setTimestamp(15, Timestamp.valueOf(resetCodeCreationTime));
            } else {
                staffStatement.setNull(15, java.sql.Types.TIMESTAMP);
            }
            staffStatement.setBoolean(16, staff.isResetCodeUsed());

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

    // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
    private boolean isEmailExists(String email, Connection conn) throws SQLException {
        String query = "SELECT COUNT(*) FROM Staffs WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
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

        if (staff.getPassword() == null || staff.getPassword().length() < 5) {
            System.out.println("Mật khẩu phải có ít nhất 5 ký tự.");
            isValid = false;
        }

        return isValid;
    }


    // Phương thức cập nhật thông tin nhân viên
    public boolean updateStaff(Staff staff) {
        String query = "UPDATE Staffs SET FirstName = ?, LastName = ?, DateOfBirth = ?, Gender = ?, Address = ?, PhoneNumber = ?, Email = ?, Password = ?, HireDate = ?, Salary = ?, EducationBackground = ?, Experience = ?, Avatar = ?, Status = ?, ResetCode = ?, ResetCodeCreationTime = ?, IsResetCodeUsed = ? WHERE StaffID = ?";

        try (Connection conn = ConnectDB.connection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            String password = staff.getPassword();
            if (password != null && !password.isEmpty()) {
                password = PasswordUtil.hashPassword(password);
            }

            preparedStatement.setString(1, staff.getFirstName());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setDate(3, staff.getDateOfBirth());
            preparedStatement.setByte(4, staff.getGender());
            preparedStatement.setString(5, staff.getAddress());
            preparedStatement.setString(6, staff.getPhoneNumber());
            preparedStatement.setString(7, staff.getEmail());
            preparedStatement.setString(8, password);
            preparedStatement.setDate(9, staff.getHireDate());
            preparedStatement.setDouble(10, staff.getSalary());
            preparedStatement.setString(11, staff.getEducationBackground());
            preparedStatement.setString(12, staff.getExperience());
            preparedStatement.setString(13, staff.getAvatar());
            preparedStatement.setString(14, staff.getStatus());
            preparedStatement.setString(15, staff.getResetCode());

            if (staff.getResetCodeCreationTime() != null) {
                preparedStatement.setTimestamp(16, Timestamp.valueOf(staff.getResetCodeCreationTime()));
            } else {
                preparedStatement.setNull(16, java.sql.Types.TIMESTAMP);
            }
            preparedStatement.setBoolean(17, staff.isResetCodeUsed());
            preparedStatement.setInt(18, staff.getStaffID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức xóa nhân viên (thay vì xóa thực tế, cập nhật trạng thái)
    public boolean deleteStaff(int staffID) {
        String query = "UPDATE Staffs SET Status = 'inactive' WHERE StaffID = ?";

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
        String query = "SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed " +
                "FROM Staffs WHERE StaffID = ?";

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
        StringBuilder query = new StringBuilder("SELECT StaffID, FirstName, LastName, DateOfBirth, Gender, Address, PhoneNumber, Email, Password, HireDate, Salary, EducationBackground, Experience, Avatar, Status, ResetCode, ResetCodeCreationTime, IsResetCodeUsed FROM Staffs WHERE Status = 'active'");

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







}