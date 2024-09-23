package com.app.schoolmanagementsystem.model;

import java.time.LocalDate;

public class ClassModel {
    private int classID;
    private String className;
    private String section;
    private int staffID;
    private LocalDate enrollmentDate;
    private LocalDate completeDate;
    private String teacherName;
    private String teacherPhoneNumber;

    public ClassModel(int classID, String className, String section, int staffID, LocalDate enrollmentDate, LocalDate completeDate, String teacherName, String teacherPhoneNumber) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
        this.enrollmentDate = enrollmentDate;
        this.completeDate = completeDate;
        this.teacherName = teacherName;
        this.teacherPhoneNumber = teacherPhoneNumber;
    }

    public ClassModel(int classID, String className, String section, int staffID) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
    }

    public ClassModel(int classID, String className, String section, int staffID, LocalDate enrollmentDate, LocalDate completeDate) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
        this.enrollmentDate = enrollmentDate;
        this.completeDate = completeDate;
    }

    public ClassModel(int classID, String className, String section, LocalDate enrollmentDate, LocalDate completeDate) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.enrollmentDate = enrollmentDate;
        this.completeDate = completeDate;
    }

    // Getters and Setters

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

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public LocalDate getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(LocalDate completeDate) {
        this.completeDate = completeDate;
    }

    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getAcademicYear() {
        if (enrollmentDate != null && completeDate != null) {
            return enrollmentDate.toString() + " - " + completeDate.toString();
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return className;
    }
}
