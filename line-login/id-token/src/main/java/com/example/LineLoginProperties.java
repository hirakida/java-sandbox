package com.example;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "line-login")
@Validated
@Data
public class LineLoginProperties {
    @NotNull
    private String channelId;
    @NotNull
    private String channelSecret;
    @NotNull
    private String redirectUri;
}
