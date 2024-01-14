package com.spring.security.tutorial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/hello")
    public  String hello(){
        return  "Hello welcome to daily code with me";
    }
}
