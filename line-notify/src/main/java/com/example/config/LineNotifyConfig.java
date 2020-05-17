package com.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import com.example.controller.OAuthSession;

@Configuration
@EnableConfigurationProperties(LineNotifyProperties.class)
public class LineNotifyConfig {

    @Bean
    @SessionScope
    public OAuthSession oAuthSession() {
        return new OAuthSession();
    }
}
