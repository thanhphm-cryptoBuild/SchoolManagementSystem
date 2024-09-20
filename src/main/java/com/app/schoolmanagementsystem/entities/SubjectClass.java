package com.app.schoolmanagementsystem.entities;

public class SubjectClass {
    private int classSubjectID;
    private int classID;
    private int subjectID;
    private int staffID;

    public SubjectClass(int classSubjectID, int classID, int subjectID, int staffID) {
        this.classSubjectID = classSubjectID;
        this.classID = classID;
        this.subjectID = subjectID;
        this.staffID = staffID;
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

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }
}
