package com.spring.security.tutorial.controller;

import com.spring.security.tutorial.entity.EmailDetails;
import com.spring.security.tutorial.entity.User;
import com.spring.security.tutorial.entity.VerificationToken;
import com.spring.security.tutorial.event.RegistrationCompleteEvent;
import com.spring.security.tutorial.models.PasswordModel;
import com.spring.security.tutorial.models.UserModel;
import com.spring.security.tutorial.service.EmailService;
import com.spring.security.tutorial.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@Slf4j
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @PostMapping("/register")
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) throws Exception{
        User user=userService.registerUser(userModel);
        publisher.publishEvent(new RegistrationCompleteEvent(user,applicationUrl(request)));
        return "success";
    }
    @GetMapping("/verifyRegistration")
    public String verifyRegistration(@RequestParam("token") String token){
        String result=userService.validateVerificationToken(token);
        if(result.equalsIgnoreCase("valid")){
            return "User verified successfully";
        }
        return "bad User";
    }


    private String applicationUrl(HttpServletRequest request) {
        return  "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
    @PostMapping("/sendMail")
    public  String sendMail(@RequestBody EmailDetails details){
        String status=emailService.sendSimpleMail(details);
        return  status;
    }

 @GetMapping("/resendVerifyToken")
 public  String resendVerificationToken(@RequestParam("token") String oldToken,HttpServletRequest request){
//        getting new verificationToken
VerificationToken verificationToken=userService.generateNewVerificationToken(oldToken);
User user=verificationToken.getUser();
resendVerificationTokenMail(user,applicationUrl(request),verificationToken);
 return "verification link send ";
    }
    @PostMapping("/resetPassword")
    public String  resetPassword(@RequestBody PasswordModel passwordModel,HttpServletRequest request){
        User user=userService.findUserByEmail(passwordModel.getEmail());
        String url="";
        if(user !=null){
            String token=UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user,token);
            url=passwordResetTokenMail(user,applicationUrl(request),token);
//            request indicates it will  make a request to the server
        }
        return url;
    }
    @PostMapping("/savePassword")
public String savePassword(@RequestParam("token") String token,@RequestBody PasswordModel passwordModel){
String result=userService.validatePasswordResetToken(token);
if(!result.equalsIgnoreCase("valid")){
    return "invalid token";
}
Optional<User> user=userService.getUserByPasswordResetToken(token);
if(user.isPresent()){
    userService.changePassword(user.get(),passwordModel.getNewPassword());

    return  "Password reset successfully";
}
else{
    return "invalid token";
}
}
@PostMapping("/changePassword")
public String changePassword(@RequestBody PasswordModel passwordModel){
        User user=userService.findUserByEmail(passwordModel.getEmail());
        if(!userService.checkIfValidOldPassword(user,passwordModel.getOldPassword())){

            return "invalid old password";
    }
        else {

            userService.changePassword(user,passwordModel.getNewPassword());
            return "password changed successfully";
        }
}
    private String passwordResetTokenMail(User user, String applicationUrl, String token) {
        String url=applicationUrl+"/savePassword?token="+token;
//        //send Verification Email
        log.info("click link to reset your password :"+url);
        return url;
    }

    private void resendVerificationTokenMail(User user, String applicationUrl,VerificationToken verificationToken) {
        String url=applicationUrl+"/verifyRegistration?token="+verificationToken.getToken();
//        //send Verification Email
        log.info("click on  to verify your registration:"+url);
    }
}

//conclusion
//autowired looks for the beans that implements that class
// and interface helps to hidden unnecessary implementations