package com.example;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import lombok.Data;

@Component
@SessionScope
@Data
@SuppressWarnings("serial")
public class LineLoginSession implements Serializable {
    private String state;
    private String accessToken;
    private String refreshToken;
}
