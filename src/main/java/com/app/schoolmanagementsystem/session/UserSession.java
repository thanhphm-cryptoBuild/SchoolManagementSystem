package com.app.schoolmanagementsystem.session;

public class UserSession {
    private static String currentRoleName;

    public static void setCurrentRoleName(String roleName) {
        currentRoleName = roleName;
    }

    public static String getCurrentRoleName() {
        return currentRoleName;
    }
}
