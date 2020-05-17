package com.example.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(SnakeCaseStrategy.class)
@Data
public class Token {
    private String scope;
    private String accessToken;
    private String idToken;
    private String tokenType;
    private int expiresIn;
    private String refreshToken;
}
