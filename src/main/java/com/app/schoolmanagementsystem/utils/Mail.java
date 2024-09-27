package com.app.schoolmanagementsystem.utils;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Mail {

    private final String username = "nguyennhan03012016@gmail.com";
    private final String password = "vfmm jpkk ytns dvay";

    public void sendEmail(String toAddress, String subject, String messageBody) throws MessagingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
        message.setSubject(subject);
        message.setText(messageBody);

        Transport.send(message);
    }
}