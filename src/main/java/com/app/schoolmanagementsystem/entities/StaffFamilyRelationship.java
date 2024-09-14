package com.app.schoolmanagementsystem.entities;

public class StaffFamilyRelationship {
    private int StaffID;
    private int FamilyID;

    public StaffFamilyRelationship() {
    }

    public StaffFamilyRelationship(int staffID, int familyID) {
        StaffID = staffID;
        FamilyID = familyID;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }

    public int getFamilyID() {
        return FamilyID;
    }

    public void setFamilyID(int familyID) {
        FamilyID = familyID;
    }
}
