package com.example.web.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Value;

@JsonNaming(SnakeCaseStrategy.class)
@Value
public class AccessTokenVerify {
    String scope;
    String clientId;
    int expiresIn;
}
