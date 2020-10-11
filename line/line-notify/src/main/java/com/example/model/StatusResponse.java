package com.example.model;

import lombok.Data;

@Data
public class StatusResponse {
    private final Status status;
    private final RateLimit rateLimit;
}
