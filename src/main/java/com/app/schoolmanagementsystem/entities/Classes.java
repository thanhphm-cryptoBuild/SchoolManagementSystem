package com.app.schoolmanagementsystem.entities;

public class Classes {
    private int ClassID;
    private String ClassName;
    private int Year;
    private String Section;
    private int TeacherID;

    public Classes() {
    }

    public Classes(int classID, String className, int year, String section, int teacherID) {
        ClassID = classID;
        ClassName = className;
        Year = year;
        Section = section;
        TeacherID = teacherID;
    }

    public int getClassID() {
        return ClassID;
    }

    public void setClassID(int classID) {
        ClassID = classID;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public int getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(int teacherID) {
        TeacherID = teacherID;
    }
}
