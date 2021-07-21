package com.nineleaps.supplierservice.email.impl;


import com.nineleaps.supplierservice.email.EmailService;
import com.nineleaps.supplierservice.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @author Dilip Chauhan
 * Created on 30/03/2020
 */
@Slf4j
@Component
public class EmailServiceImpl implements EmailService {

    public final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendSimpleMessage(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(Email email, String attachmentFilename, String attachmentPath) {

        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getText());
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            helper.addAttachment(attachmentFilename, file);
        } catch (MessagingException ex) {
            log.error("Error while sending mail to:" + email.getTo(), ex);
        }
        emailSender.send(message);
    }
}
