package com.app.schoolmanagementsystem.model;

public class StudentFamilyModel {
    private int familyID;
    private int studentID;
    private String fatherName;
    private String fatherPhoneNumber;
    private String motherName;
    private String motherPhoneNumber;

    public StudentFamilyModel(int familyID, int studentID, String fatherName, String fatherPhoneNumber, String motherName, String motherPhoneNumber) {
        this.familyID = familyID;
        this.studentID = studentID;
        this.fatherName = fatherName;
        this.fatherPhoneNumber = fatherPhoneNumber;
        this.motherName = motherName;
        this.motherPhoneNumber = motherPhoneNumber;
    }

    public int getFamilyID() {
        return familyID;
    }

    public void setFamilyID(int familyID) {
        this.familyID = familyID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherPhoneNumber() {
        return fatherPhoneNumber;
    }

    public void setFatherPhoneNumber(String fatherPhoneNumber) {
        this.fatherPhoneNumber = fatherPhoneNumber;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherPhoneNumber() {
        return motherPhoneNumber;
    }

    public void setMotherPhoneNumber(String motherPhoneNumber) {
        this.motherPhoneNumber = motherPhoneNumber;
    }
}
