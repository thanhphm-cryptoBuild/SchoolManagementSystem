package com.app.schoolmanagementsystem.utils;

import javax.mail.MessagingException;

public class EmailTest {

    public static void main(String[] args) {
        String toAddress = "binhocit22t@gmail.com";
        String subject = "Test Email";
        String messageBody = "This is a test email sent from Java.";

        Mail mail = new Mail();

        try {
            mail.sendEmail(toAddress, subject, messageBody);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send email.");
        }
    }
}