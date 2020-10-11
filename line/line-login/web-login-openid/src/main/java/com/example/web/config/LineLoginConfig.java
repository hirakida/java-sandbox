package com.example.web.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.example.web.model.LoginSession;

@EnableConfigurationProperties(LineLoginProperties.class)
@Configuration
public class LineLoginConfig {
    public static final String AUTHORIZATION_BASE_URL = "https://access.line.me";

    @Bean
    @SessionScope
    public LoginSession loginSession() {
        return new LoginSession();
    }
}
