package com.spring.security.tutorial.service;

import com.spring.security.tutorial.entity.PasswordResetToken;
import com.spring.security.tutorial.entity.User;
import com.spring.security.tutorial.entity.VerificationToken;
import com.spring.security.tutorial.models.UserModel;
import com.spring.security.tutorial.repository.PasswordResetTokenRepository;
import com.spring.security.tutorial.repository.UserRepository;
import com.spring.security.tutorial.repository.VerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
@Autowired
public UserRepository userRepository;
@Autowired
private PasswordResetTokenRepository passwordResetTokenRepository;
@Autowired
public VerificationRepository verificationRepository;
//@Autowired
@Autowired
private PasswordEncoder passwordEncoder;
    @Override
    public User registerUser(UserModel userModel) {
 User user=new User();
 user.setFirstName(userModel.getFirstName());
 user.setLastName(userModel.getLastName());
 user.setEmail(userModel.getEmail());
 user.setPassword(passwordEncoder.encode(userModel.getPassword()));
 user.setRole("user");
        return userRepository.save(user);
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken=new VerificationToken(user,token);
   verificationRepository.save(verificationToken);
    }
    @Override
    public  String validatePasswordResetToken(String token){
        PasswordResetToken passwordResetToken=passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken==null){
            return "invalid";
        }
        User user=passwordResetToken.getUser();
        Calendar cal=Calendar.getInstance();
        if(passwordResetToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0){
            passwordResetTokenRepository.delete(passwordResetToken);
     return "expired";
        }

        return  "valid";
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUser()) ;
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword,user.getPassword());
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken=verificationRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user,String token) {
        PasswordResetToken passwordResetToken=new PasswordResetToken(user,token);
passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validateVerificationToken(String token) {

        VerificationToken verificationToken=verificationRepository.findByToken(token);
        if(verificationToken==null){
            return "invalid";
        }
        User user=verificationToken.getUser();
        Calendar cal=Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0){
            verificationRepository.delete(verificationToken);
            return "expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return  "valid";
    }

}
