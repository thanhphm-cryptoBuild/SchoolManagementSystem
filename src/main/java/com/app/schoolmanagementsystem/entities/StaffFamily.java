package com.app.schoolmanagementsystem.entities;

public class StaffFamily {
    private int FamilyID;
    private String FamilyMemberName;
    private String Relation;
    private String ContactNumber;

    public StaffFamily() {
    }

    public StaffFamily(int familyID, String familyMemberName, String relation, String contactNumber) {
        FamilyID = familyID;
        FamilyMemberName = familyMemberName;
        Relation = relation;
        ContactNumber = contactNumber;
    }

    public int getFamilyID() {
        return FamilyID;
    }

    public void setFamilyID(int familyID) {
        FamilyID = familyID;
    }

    public String getFamilyMemberName() {
        return FamilyMemberName;
    }

    public void setFamilyMemberName(String familyMemberName) {
        FamilyMemberName = familyMemberName;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
