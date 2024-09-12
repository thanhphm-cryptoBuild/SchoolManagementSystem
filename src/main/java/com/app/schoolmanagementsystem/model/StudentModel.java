package com.app.schoolmanagementsystem.model;

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

    // Constructor
    public StudentModel(int studentID, String firstName, String lastName, Date dateOfBirth, boolean gender,
                        String address, String phoneNumber, String email, Date enrollmentDate, int classID, String status) {
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
    }

    // Getters và Setters
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
}
