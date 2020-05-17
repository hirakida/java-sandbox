package com.example.model;

import lombok.Data;

@Data
public class Profile {
    private String displayName;
    private String userId;
    private String pictureUrl;
    private String statusMessage;
}
