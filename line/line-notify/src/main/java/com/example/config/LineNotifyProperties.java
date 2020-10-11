package com.example.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "line-notify")
@Validated
@Data
public class LineNotifyProperties {
    @NotNull
    private String clientId;
    @NotNull
    private String clientSecret;
    @NotNull
    private String redirectUri;
    private String accessToken;
}
