package com.dailycodewithblaise.Oauthresourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.HttpSecurityDsl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ResourceServerConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurityDsl http) throws Exception{
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests()
                


    }
}
