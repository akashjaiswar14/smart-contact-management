package com.scm.services.impl;

import javax.tools.JavaFileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender eJavaMailSender;

    
    @Override
    public void sendMail(String to, String subject, String body) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("YOUR_EMAIL@gmail.com");

        eJavaMailSender.send(message);

    }

}
