package com.spring.security.tutorial.event.listener;

import com.spring.security.tutorial.entity.User;
import com.spring.security.tutorial.event.RegistrationCompleteEvent;
import com.spring.security.tutorial.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        //Create the verification token for the use with link
        User user=event.getUser();
        String token= UUID.randomUUID().toString();
       userService.saveVerificationTokenForUser(token,user);
       //Send mail to user
        String url=event.getApplicationUrl()+"/"+"verifyRegistration?token="+token;
//send verificationEmail()
 log.info("Click to verify your account:{}",url);
// mocking


    }



}
