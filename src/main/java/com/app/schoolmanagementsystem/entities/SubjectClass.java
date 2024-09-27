package com.app.schoolmanagementsystem.entities;

public class SubjectClass {
    private int classSubjectID;
    private int classID;
    private String classNameYear;
    private int subjectID;
    private String subjectName;
    private int staffID;
    private String teacherName;
    private String teacherPhoneNumber;

    public SubjectClass(int classSubjectID, int classID, int subjectID, int staffID) {
        this.classSubjectID = classSubjectID;
        this.classID = classID;
        this.subjectID = subjectID;
        this.staffID = staffID;
    }

    public SubjectClass(int subjectID, String subjectName, String classNameYear) {
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.classNameYear = classNameYear;
    }

    public SubjectClass(int classSubjectID, int classID, String classNameYear, int subjectID, String subjectName, int staffID, String teacherName, String teacherPhoneNumber) {
        this.classSubjectID = classSubjectID;
        this.classID = classID;
        this.classNameYear = classNameYear;
        this.subjectID = subjectID;
        this.subjectName = subjectName;
        this.staffID = staffID;
        this.teacherName = teacherName;
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public SubjectClass(int classSubjectID, String classNameYear, String subjectName, String teacherName, String teacherPhoneNumber) {
        this.classSubjectID = classSubjectID;
        this.classNameYear = classNameYear;
        this.subjectName = subjectName;
        this.teacherName = teacherName;
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public int getClassSubjectID() {
        return classSubjectID;
    }

    public void setClassSubjectID(int classSubjectID) {
        this.classSubjectID = classSubjectID;
    }

    public int getClassID() {
        return classID;
    }

    public String getClassNameYear() {
        return classNameYear;
    }

    public void setClassNameYear(String classNameYear) {
        this.classNameYear = classNameYear;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherPhoneNumber() {
        return teacherPhoneNumber;
    }

    public void setTeacherPhoneNumber(String teacherPhoneNumber) {
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public String getFormattedTeacherName() {
        if (teacherName != null && !teacherName.isEmpty()) {
            String[] parts = teacherName.split(" ");
            if (parts.length >= 2) {
                String firstName = parts[0];
                String lastName = parts[parts.length - 1];
                return firstName + " " + lastName + " (" + staffID + ")";
            } else {
                return teacherName + " (" + staffID + ")";
            }
        }
        return "";
    }

}
