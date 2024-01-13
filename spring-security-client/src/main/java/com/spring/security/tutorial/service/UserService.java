package com.spring.security.tutorial.service;

import com.spring.security.tutorial.entity.PasswordResetToken;
import com.spring.security.tutorial.entity.User;
import com.spring.security.tutorial.entity.VerificationToken;
import com.spring.security.tutorial.models.PasswordModel;
import com.spring.security.tutorial.models.UserModel;
import java.util.*;

public interface UserService {
    User registerUser(UserModel userModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    String validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    boolean checkIfValidOldPassword(User user, String oldPassword);
}
