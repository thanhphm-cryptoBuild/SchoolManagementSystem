package com.app.schoolmanagementsystem.model;

import java.util.Date;

public class ClassModel {
    private int classID;
    private String className;
    private String section;
    private int staffID;
    private Date enrollmentDate;
    private Date completeDate;



    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }



    public ClassModel(int classID, String className, String section, int staffID) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
    }

    public ClassModel(int classID, String className, String section, int staffID, Date enrollmentDate, Date completeDate) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
        this.enrollmentDate = enrollmentDate;
        this.completeDate =completeDate;
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

    @Override
    public String toString() {
        return className;
    }
}
