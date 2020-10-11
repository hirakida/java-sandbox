package com.example;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@ConfigurationProperties(prefix = "liff.liff-id")
@Validated
@Data
public class LiffProperties {
    @NotNull
    private String full;
    @NotNull
    private String tall;
    @NotNull
    private String compact;

    public String getLiffId(String viewType) {
        if ("full".equals(viewType)) {
            return getFull();
        } else if ("tall".equals(viewType)) {
            return getTall();
        } else {
            return getCompact();
        }
    }
}
