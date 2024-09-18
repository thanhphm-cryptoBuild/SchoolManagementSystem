package com.app.schoolmanagementsystem.entities;

public class StaffRoles {

    private int StaffRoleID;
    private String RoleName; // 'Admin Master', 'Manager', 'Teacher'
    private int StaffID;

    public StaffRoles(int staffRoleID, String roleName, int staffID) {
        StaffRoleID = staffRoleID;
        RoleName = roleName;
        StaffID = staffID;
    }

    // Constructor mới với 1 tham số
    public StaffRoles(int staffID, String roleName) {
        this.StaffID = staffID;
        this.RoleName = roleName;
    }


    public StaffRoles() {
    }

    public StaffRoles(String roleName) {
        this.RoleName = roleName;
    }

    public int getStaffRoleID() {
        return StaffRoleID;
    }

    public void setStaffRoleID(int staffRoleID) {
        StaffRoleID = staffRoleID;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }
}