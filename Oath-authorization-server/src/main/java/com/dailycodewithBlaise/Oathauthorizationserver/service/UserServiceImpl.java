package com.dailycodewithBlaise.Oathauthorizationserver.service;


import com.dailycodewithBlaise.Oathauthorizationserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService{
@Autowired
public UserRepository userRepository;



}
