package com.app.schoolmanagementsystem.model;

public class ClassModel {
    private int classID;
    private String className;
    private int year;
    private String section;
    private int teacherID;

    // Constructor mới
    public ClassModel(int classID, String className, int year, String section, int teacherID) {
        this.classID = classID;
        this.className = className;
        this.year = year;
        this.section = section;
        this.teacherID = teacherID;
    }

    // Getter và Setter
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

    @Override
    public String toString() {
        return className;
    }
}
