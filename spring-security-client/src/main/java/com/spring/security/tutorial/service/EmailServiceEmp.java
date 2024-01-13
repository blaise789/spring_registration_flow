package com.spring.security.tutorial.service;

import com.spring.security.tutorial.entity.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceEmp implements EmailService {
@Autowired
private JavaMailSender javaMailSender;
@Value("${spring.mail.username}")
private String sender;
    @Override
    public String sendSimpleMail(EmailDetails details) {
try{
    SimpleMailMessage mailMessage=new SimpleMailMessage();
    mailMessage.setFrom(sender);
    mailMessage.setTo(details.getRecipient());
    mailMessage.setText(details.getMsgBody());
    mailMessage.setSubject(details.getSubject());

    javaMailSender.send(mailMessage);
}
catch (MailSendException e){
    return  "Error while sending mail";
}
        return "message send successfully";
    }

    @Override
    public String sendMailWithAttachments(EmailDetails details) {
        return null;
    }
}
