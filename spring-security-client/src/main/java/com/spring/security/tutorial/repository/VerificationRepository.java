package com.spring.security.tutorial.repository;

import com.spring.security.tutorial.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository  extends JpaRepository<VerificationToken,Long> {
    VerificationToken findByToken(String token);
}
