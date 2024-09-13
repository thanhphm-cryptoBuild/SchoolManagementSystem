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

    public StaffRoles() {
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
