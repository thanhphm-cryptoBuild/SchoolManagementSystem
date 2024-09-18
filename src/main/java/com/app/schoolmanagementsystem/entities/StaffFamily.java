package com.app.schoolmanagementsystem.entities;

public class StaffFamily {
    private int familyID;
    private int staffID;
    private String familyMemberName;
    private String relationshipType;
    private String contactNumber;

    public StaffFamily(int familyID, int staffID, String familyMemberName, String relationshipType, String contactNumber) {
        this.familyID = familyID;
        this.staffID = staffID;
        this.familyMemberName = familyMemberName;
        this.relationshipType = relationshipType;
        this.contactNumber = contactNumber;
    }

    public StaffFamily(int staffID, String familyMemberName, String relationshipType, String contactNumber) {
        this.staffID = staffID;
        this.familyMemberName = familyMemberName;
        this.relationshipType = relationshipType;
        this.contactNumber = contactNumber;
    }

    public StaffFamily(String familyMemberName, String relationshipType, String contactNumber) {
        this.familyMemberName = familyMemberName;
        this.relationshipType = relationshipType;
        this.contactNumber = contactNumber;
    }

    public StaffFamily() {
    }

    public int getFamilyID() {
        return familyID;
    }

    public void setFamilyID(int familyID) {
        this.familyID = familyID;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getFamilyMemberName() {
        return familyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        this.familyMemberName = familyMemberName;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}