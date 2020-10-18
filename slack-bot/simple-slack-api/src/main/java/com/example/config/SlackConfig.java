package com.example.config;

import java.io.IOException;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;

@Configuration
@EnableConfigurationProperties(SlackProperties.class)
public class SlackConfig {

    @Bean(destroyMethod = "disconnect")
    public SlackSession slackSession(SlackProperties properties) throws IOException {
        SlackSession session = SlackSessionFactory.createWebSocketSlackSession(properties.getToken());
        session.connect();
        return session;
    }
}
