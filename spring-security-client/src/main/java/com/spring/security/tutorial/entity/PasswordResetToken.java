package com.spring.security.tutorial.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
public class PasswordResetToken      {



        //    expiration time in min
        private  static final int EXPIRATION_TIME=10;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private  Long id;
        private  String token;
        private Date expirationTime;
        @OneToOne(fetch = FetchType.EAGER,
                cascade = CascadeType.ALL
        )
        @JoinColumn(name = "user_Id",
                nullable=false,
                foreignKey = @ForeignKey(name="FK_USER_PASSWORD_RESET_TOKEN")

        )
        private  User user;
        public PasswordResetToken(User user, String token){
            super();
            this.token=token;
            this.user=user;
            this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
        }
        public PasswordResetToken(String token){
            super();
            this.token=token;
            this.expirationTime=calculateExpirationDate(EXPIRATION_TIME);
        }

        private Date calculateExpirationDate(int expirationTime) {
            Calendar calendar=Calendar.getInstance();
            calendar.setTimeInMillis(new Date().getTime());
//         set the time of the current calendar in mils
            calendar.add(Calendar.MINUTE,expirationTime);
            return new Date(calendar.getTime().getTime());

        }





}
