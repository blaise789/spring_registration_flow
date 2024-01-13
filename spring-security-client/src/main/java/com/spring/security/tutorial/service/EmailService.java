package com.spring.security.tutorial.service;

import com.spring.security.tutorial.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);
    String sendMailWithAttachments(EmailDetails details);
}
