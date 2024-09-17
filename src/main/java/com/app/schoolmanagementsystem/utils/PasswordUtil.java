package com.app.schoolmanagementsystem.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Phương thức mã hóa mật khẩu
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Phương thức kiểm tra mật khẩu
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}