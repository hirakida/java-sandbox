package com.example.model;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private ObjectId id;
    private long userId;
    private String name;
    private int age;
    private long roleId;
    private LocalDateTime createdAt;

    public User(long userId, String name, int age, long roleId) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.roleId = roleId;
        createdAt = LocalDateTime.now();
    }
}
