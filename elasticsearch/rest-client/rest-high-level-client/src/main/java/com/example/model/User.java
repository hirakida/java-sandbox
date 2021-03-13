package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public static final String FIELD_USER_ID = "userId";
    public static final String FIELD_NAME = "name";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_AGE = "age";
    public static final String FIELD_SCORE = "score";

    private long userId;
    private String name;
    private String email;
    private int age;
    private double score;
}
