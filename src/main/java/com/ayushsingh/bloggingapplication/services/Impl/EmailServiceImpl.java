package com.ayushsingh.bloggingapplication.services.Impl;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.constants.EmailConstants;
import com.ayushsingh.bloggingapplication.services.EmailService;


@Service
public class EmailServiceImpl implements EmailService {
    private static final String PORT_KEY = "mail.smtp.port";
    private static final String PORT_VAL = "465";
    
    private static final String HOST_KEY = "mail.smtp.host";
    private static final String HOST_VAL = "smtp.gmail.com";

    private static final String AUTH_KEY = "mail.smtp.auth";

    private static final String SSL_ENABLE_KEY = "mail.smtp.ssl.enable";
    private static final String SSL_ENABLE_VAL = "true";
    @Override
    public boolean sendEmail(String subject, String message, String to) {
         boolean isEmailSent=false;
       
        // get system properties
        Properties properties = System.getProperties();
        System.out.println("Properties: " + properties);

        // setting important information to properties object

        // host set
        properties.put(HOST_KEY,HOST_VAL);
        properties.put(PORT_KEY, PORT_VAL);
        properties.put(SSL_ENABLE_KEY, SSL_ENABLE_VAL);
        properties.put(AUTH_KEY, SSL_ENABLE_VAL);

         // Step 1: Get session object
         Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EmailConstants.FROM, EmailConstants.MY_PASSWORD);
            }
            
        });
        // to provide debug functionality
        session.setDebug(true);
        // Step 2: Compose the message [text,multi-media]
        MimeMessage mimeMessage = new MimeMessage(session);

        // from email
        try {
            mimeMessage.setFrom(EmailConstants.FROM);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to)/*
                                                                                       * Can pass an array of addresses
                                                                                       */);
            // add subject
            mimeMessage.setSubject(subject);

            // adding text to message
            // mimeMessage.setText(message);
            mimeMessage.setContent(message,"text/html");

            // Send the message using transport class
            Transport.send(mimeMessage);
            System.out.println("Email sent successfully");
            isEmailSent=true;
        } catch (MessagingException e) {
            System.out.println("Error occurred while sending email");
            e.printStackTrace();
        }
        return isEmailSent;
        
    }
    
}
