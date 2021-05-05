package com.example.web.model;

import lombok.Value;

@Value
public class Profile {
    String displayName;
    String userId;
    String pictureUrl;
    String statusMessage;
}
