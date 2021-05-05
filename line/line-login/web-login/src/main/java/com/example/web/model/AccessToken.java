package com.example.web.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Value;

@JsonNaming(SnakeCaseStrategy.class)
@Value
public class AccessToken {
    String scope;
    String accessToken;
    String tokenType;
    int expiresIn;
    String refreshToken;
}
