package com.app.schoolmanagementsystem.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Staff {
    private int staffID;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Byte gender;
    private String address;
    private String phoneNumber;
    private String email;
    private String password; // Mật khẩu có thể được mã hóa
    private Date hireDate;
    private String positionName;

    private double salary;
    private String educationBackground;
    private String experience;
    private String status;
    private String resetCode;
    private LocalDateTime resetCodeCreationTime;
    private boolean isResetCodeUsed;
    private String otherField;
    private String avatar;
    private boolean isExternalAvatar;
    public String getOtherField() {
        return otherField;
    }

    public void setOtherField(String otherField) {
        this.otherField = otherField;
    }


    public Staff() {
    }


    public Staff(int staffID, String firstName, String lastName, Date dateOfBirth, Byte gender, String address, String phoneNumber, String email, String password, Date hireDate, String positionName, double salary, String educationBackground, String experience, String avatar, String status, String resetCode, LocalDateTime resetCodeCreationTime, boolean isResetCodeUsed) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.hireDate = hireDate;
        this.positionName = positionName;
        this.salary = salary;
        this.educationBackground = educationBackground;
        this.experience = experience;
        this.avatar = avatar;
        this.status = status;
        this.resetCode = resetCode;
        this.resetCodeCreationTime = resetCodeCreationTime;
        this.isResetCodeUsed = isResetCodeUsed;
    }

    public Staff(int staffID, String firstName, String lastName, Date dateOfBirth, Byte gender, String address, String phoneNumber, String email, String password, Date hireDate, String positionName, double salary, String educationBackground, String experience, String avatar, String status) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.hireDate = hireDate;
        this.positionName = positionName;
        this.salary = salary;
        this.educationBackground = educationBackground;
        this.experience = experience;
        this.avatar = avatar;
        this.status = status;
    }
    public Staff(int staffID, String firstName, String lastName, String positionName) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.positionName = positionName;
    }

    public Staff(int staffID, String firstName, String lastName, String phoneNumber, String positionName) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.positionName = positionName;
    }

    public Staff(int staffID, String firstName, String lastName, String phoneNumber, String positionName, String otherField) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.positionName = positionName;
        this.otherField = otherField;
    }

    public Staff(int staffID, String firstName, String lastName, Byte gender, String phoneNumber, String email, String positionName, String avatar, String status) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.positionName = positionName;
        this.avatar = avatar;
        this.status = status;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getEducationBackground() {
        return educationBackground;
    }

    public void setEducationBackground(String educationBackground) {
        this.educationBackground = educationBackground;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
        this.isExternalAvatar = avatar != null && (avatar.startsWith("http://") || avatar.startsWith("https://") || avatar.startsWith("file:/"));
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResetCode() {
        return resetCode;
    }

    public void setResetCode(String resetCode) {
        this.resetCode = resetCode;
    }

    public LocalDateTime getResetCodeCreationTime() {
        return resetCodeCreationTime;
    }

    public void setResetCodeCreationTime(LocalDateTime resetCodeCreationTime) {
        this.resetCodeCreationTime = resetCodeCreationTime;
    }

    public boolean isResetCodeUsed() {
        return isResetCodeUsed;
    }

    public void setResetCodeUsed(boolean resetCodeUsed) {
        isResetCodeUsed = resetCodeUsed;
    }

    @Override
    public String toString() {

        if (this.staffID == -1) {
            return "Select Teacher";
        }

        return firstName + " " + lastName + " (" + positionName + ")";
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Lấy đường dẫn đầy đủ đến avatar dựa trên nguồn của nó.
     *
     * @return Đường dẫn đầy đủ đến ảnh avatar.
     */
    public String getFullAvatarPath() {
        if (isExternalAvatar()) {
            return avatar;
        } else {
            // Trả về đường dẫn tài nguyên nội bộ
            return "/com/app/schoolmanagementsystem/images/" + avatar;
        }
    }

    /**
     * Kiểm tra xem avatar có phải từ nguồn bên ngoài hay không.
     *
     * @return true nếu avatar là từ nguồn bên ngoài, ngược lại false.
     */
    public boolean isExternalAvatar() {
        return avatar != null && (avatar.startsWith("http://") || avatar.startsWith("https://") || avatar.startsWith("file:/"));
    }



}