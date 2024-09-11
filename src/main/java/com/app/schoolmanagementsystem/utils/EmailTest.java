package com.app.schoolmanagementsystem;

import com.app.schoolmanagementsystem.utils.Mail;

public class EmailTest {

    public static void main(String[] args) {
        // Thông tin email gửi
        String fromEmail = "your-email@gmail.com";  // Thay bằng email của bạn
        String password = "your-email-password";  // Thay bằng mật khẩu ứng dụng của bạn
        String smtpHost = "smtp.gmail.com";  // SMTP của Gmail
        String smtpPort = "587";  // Cổng SMTP cho TLS

        // Tạo đối tượng Mail
        Mail mail = new Mail(fromEmail, password, smtpHost, smtpPort);

        // Thông tin email người nhận
        String recipientEmail = "recipient-email@example.com";  // Email người nhận
        String subject = "Test Email";  // Tiêu đề email
        String body = "This is a test email sent from the Mail class.";  // Nội dung email

        // Gửi email
        mail.sendEmail(recipientEmail, subject, body);
    }
}
