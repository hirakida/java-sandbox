package com.example.model;

import lombok.Data;

@Data
public class RateLimit {
    private String limit;
    private String remaining;
    private String imageLimit;
    private String imageRemaining;
    private String reset;
}
