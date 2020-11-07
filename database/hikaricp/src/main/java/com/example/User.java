package com.example;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class User {
    private long id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
