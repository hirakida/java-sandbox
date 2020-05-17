package com.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import com.example.web.LoginSession;

@Configuration
@EnableConfigurationProperties(LineLoginProperties.class)
public class LineLoginConfig {
    public static final String LOGIN_BASE_URL = "https://access.line.me";

    @Bean
    @SessionScope
    public LoginSession loginSession() {
        return new LoginSession();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
