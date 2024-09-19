package com.app.schoolmanagementsystem.utils;

import javax.mail.MessagingException;

public class EmailTest {

    public static void main(String[] args) {
        // Thay đổi địa chỉ email nhận thử nghiệm và nội dung email theo nhu cầu của bạn
        String toAddress = "binhocit22t@gmail.com"; // Địa chỉ email nhận thử nghiệm
        String subject = "Test Email";
        String messageBody = "This is a test email sent from Java.";

        // Tạo đối tượng Mail
        Mail mail = new Mail();

        try {
            // Gửi email
            mail.sendEmail(toAddress, subject, messageBody);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email.");
        }
    }
}