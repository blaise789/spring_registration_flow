package com.dailycodewithBlaise.Oathauthorizationserver.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String LastName;
    private String email;
    @Column(length = 60)
    private  String password;
    private  String matchingPassword;
    private String role;
    private  boolean enabled=false;
//    @OneToOne(mappedBy = "user")
//    private  VerificationToken verificationToken;


}
