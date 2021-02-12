package com.example.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import com.example.model.Token;

@Configuration
@EnableConfigurationProperties(RwsProperties.class)
public class RwsConfig {
    public static final String RWS_URL = "https://app.rakuten.co.jp";

    @Bean
    @SessionScope
    public Token accessToken() {
        return new Token();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
