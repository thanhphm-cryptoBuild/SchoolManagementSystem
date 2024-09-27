package com.app.schoolmanagementsystem.entities;

public class Grades {
    private int gradeID;
    private int studentID;
    private int subjectID;
    private double marks;
    private String subjectName;

    public Grades(int gradeID, int studentID, int subjectID, double marks, String subjectName) {
        this.gradeID = gradeID;
        this.studentID = studentID;
        this.subjectID = subjectID;
        this.marks = marks;
        this.subjectName = subjectName;
    }

    public int getGradeID() {
        return gradeID;
    }

    public void setGradeID(int gradeID) {
        this.gradeID = gradeID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}