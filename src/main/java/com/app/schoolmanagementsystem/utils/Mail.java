package com.app.schoolmanagementsystem.utils;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class Mail {

    private String fromEmail;
    private String password;
    private String smtpHost;
    private String smtpPort;

    public Mail(String fromEmail, String password, String smtpHost, String smtpPort) {
        this.fromEmail = fromEmail;
        this.password = password;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;
    }

    public void sendEmail(String recipientEmail, String subject, String body) {
        // Cấu hình thuộc tính cho phiên làm việc với máy chủ SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.ssl.trust", smtpHost);

        // Tạo phiên làm việc với thông tin đăng nhập
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // Tạo đối tượng Message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));  // Email người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));  // Email người nhận
            message.setSubject(subject);  // Tiêu đề email
            message.setText(body);  // Nội dung email

            // Gửi email
            Transport.send(message);
            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
