package com.example.controller;

import java.io.Serializable;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class OAuthSession implements Serializable {
    private String state;
    private String accessToken;
}
