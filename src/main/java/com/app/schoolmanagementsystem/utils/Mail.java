package com.app.schoolmanagementsystem.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mail {

    private final String username = "nguyennhan03012016@gmail.com"; // Thay bằng địa chỉ email của bạn
    private final String password = "vfmm jpkk ytns dvay"; // Thay bằng mật khẩu ứng dụng của bạn

    // Gửi email
    public void sendEmail(String toAddress, String subject, String messageBody) throws MessagingException {
        // Thiết lập thuộc tính kết nối
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Tạo phiên làm việc
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Tạo thông điệp email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(messageBody);

        // Gửi email
        Transport.send(message);
    }
}