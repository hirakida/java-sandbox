package com.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.example.web.model.LoginSession;

@Configuration
public class LineLoginConfig {
    @Bean
    @SessionScope
    public LoginSession loginSession() {
        return new LoginSession();
    }
}
