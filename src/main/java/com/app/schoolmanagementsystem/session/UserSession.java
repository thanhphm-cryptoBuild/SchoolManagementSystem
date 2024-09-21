package com.app.schoolmanagementsystem.session;

import com.app.schoolmanagementsystem.entities.Staff;

public class UserSession {
    private static String currentRoleName;

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
}
