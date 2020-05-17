package com.example.web;

import java.io.Serializable;

import com.example.model.Token;

import lombok.Data;

@Data
@SuppressWarnings("serial")
public class LoginSession implements Serializable {
    private String state;
    private String nonce;
    private Token token;
}
