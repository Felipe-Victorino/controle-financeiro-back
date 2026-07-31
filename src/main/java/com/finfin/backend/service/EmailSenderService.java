package com.finfin.backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    @Async
    public void sendEmail(String recipient, String subject, String content){
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(recipient);
        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);
    }

    @Async
    public void sendTemplatedEmail(String recipient, String subject, String template, Context context){
        String templateString = templateEngine.process(template, context);
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try{
            messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(recipient);
            messageHelper.setSubject(subject);
            messageHelper.setText(templateString, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);

    }
}
