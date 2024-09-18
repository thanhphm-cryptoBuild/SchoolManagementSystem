package com.app.schoolmanagementsystem.model;

public class ClassModel {
    private int classID;
    private String className;
    private String section;
    private int staffID;

    // Updated constructor
    public ClassModel(int classID, String className, String section, int staffID) {
        this.classID = classID;
        this.className = className;
        this.section = section;
        this.staffID = staffID;
    }

    // Getters and Setters
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
