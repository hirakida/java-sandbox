package com.example.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "slack")
@Validated
@Data
public class SlackProperties {
    @NotNull
    private String botUserName;
    @NotNull
    private String token;
    @NotNull
    private String channel;
}
