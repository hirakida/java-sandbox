package com.example.web.model;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class LoginSession implements Serializable {
    private String state;
    private String codeVerifier;
    private String accessToken;
    private String refreshToken;
}
