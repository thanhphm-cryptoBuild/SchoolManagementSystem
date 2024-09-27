package com.app.schoolmanagementsystem.model;

import com.app.schoolmanagementsystem.utils.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StudentModel {
    private int studentID;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private boolean gender;
    private String address;
    private String phoneNumber;
    private String email;
    private Date enrollmentDate;
    private int classID;
    private String previousSchool;
    private String reasonForLeaving;
    private String status;
    private int stt;
    private String avatar;
    private boolean isExternalAvatar;

    public StudentModel(int studentID, String firstName, String lastName, Date dateOfBirth, boolean gender,
                        String address, String phoneNumber, String email, Date enrollmentDate, int classID, String status, String avatar) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.enrollmentDate = enrollmentDate;
        this.classID = classID;
        this.status = status;
        setAvatar(avatar);
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
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

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
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

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getPreviousSchool() {
        return previousSchool;
    }

    public void setPreviousSchool(String previousSchool) {
        this.previousSchool = previousSchool;
    }

    public String getReasonForLeaving() {
        return reasonForLeaving;
    }

    public void setReasonForLeaving(String reasonForLeaving) {
        this.reasonForLeaving = reasonForLeaving;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSTT() {
        return stt;
    }

    public void setSTT(int stt) {
        this.stt = stt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        if (avatar != null && !avatar.isEmpty()) {
            this.avatar = avatar;
            this.isExternalAvatar = avatar.startsWith("http://") || avatar.startsWith("https://") || avatar.startsWith("file:/");
        } else {
            this.avatar = "default_avatar.png";
            this.isExternalAvatar = false;
        }
    }

    public boolean isExternalAvatar() {
        return isExternalAvatar;
    }

    public void setExternalAvatar(boolean external) {
        this.isExternalAvatar = external;
    }

    public String getFullAvatarPath() {
        if (isExternalAvatar) {
            return avatar;
        } else {
            return "/com/app/schoolmanagementsystem/images/" + avatar;
        }
    }

    public StudentModel() {
    }


    public int countActiveStudent() {
        int count = 0;
        String query = "SELECT COUNT(*) AS totalActiveStaff FROM Students WHERE Status = ?";

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
}
