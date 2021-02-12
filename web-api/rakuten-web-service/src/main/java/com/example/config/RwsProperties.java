package com.example.config;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "rws")
@Validated
@Data
public class RwsProperties {
    @NotNull
    private String applicationId;
    @NotNull
    private String applicationSecret;
    @NotNull
    private String redirectUri;
}
