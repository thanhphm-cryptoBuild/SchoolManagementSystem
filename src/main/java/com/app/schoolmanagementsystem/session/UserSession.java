package com.app.schoolmanagementsystem.session;

import com.app.schoolmanagementsystem.entities.Staff;

public class UserSession {
    private static String currentRoleName;
    private static String staffAvatar;

    public static void setCurrentRoleName(String roleName) {
        currentRoleName = roleName;
    }

    public static String getCurrentRoleName() {
        return currentRoleName;
    }

    private static Staff currentStaff;

    // Getter và setter cho currentStaff
    public static Staff getCurrentStaff() {
        return currentStaff;
    }

    public static void setCurrentStaff(Staff staff) {
        currentStaff = staff;
    }

    public static String getStaffAvatar() {
        return staffAvatar;
    }

    public static void setStaffAvatar(String staffAvatar) {
        UserSession.staffAvatar = staffAvatar;
    }
}
