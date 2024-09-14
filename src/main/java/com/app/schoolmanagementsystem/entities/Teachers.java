package com.app.schoolmanagementsystem.entities;

public class Teachers {
    private int TeacherID;
    private int StaffID;
    private String SubjectSpecialization;
    private String ClassesTaught;
    private String Qualifications;
    private int YearsOfExperience;

    public Teachers() {
    }

    public Teachers(int teacherID, int staffID, String subjectSpecialization, String classesTaught, String qualifications, int yearsOfExperience) {
        TeacherID = teacherID;
        StaffID = staffID;
        SubjectSpecialization = subjectSpecialization;
        ClassesTaught = classesTaught;
        Qualifications = qualifications;
        YearsOfExperience = yearsOfExperience;
    }

    public int getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(int teacherID) {
        TeacherID = teacherID;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }

    public String getSubjectSpecialization() {
        return SubjectSpecialization;
    }

    public void setSubjectSpecialization(String subjectSpecialization) {
        SubjectSpecialization = subjectSpecialization;
    }

    public String getClassesTaught() {
        return ClassesTaught;
    }

    public void setClassesTaught(String classesTaught) {
        ClassesTaught = classesTaught;
    }

    public String getQualifications() {
        return Qualifications;
    }

    public void setQualifications(String qualifications) {
        Qualifications = qualifications;
    }

    public int getYearsOfExperience() {
        return YearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        YearsOfExperience = yearsOfExperience;
    }
}
