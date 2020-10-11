package com.example.model;

import lombok.Data;

@Data
public class Status {
    private int status;
    private String message;
    private String targetType;
    private String target;
}
