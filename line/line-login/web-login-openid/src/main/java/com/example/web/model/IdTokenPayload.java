package com.example.web.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(SnakeCaseStrategy.class)
@Data
public class IdTokenPayload {
    private String iss;
    private String sub;
    private String aud;
    private long exp;
    private long iat;
    private long authTime;
    private String nonce;
    private String[] amr;
    private String name;
    private String picture;
    private String email;
}
