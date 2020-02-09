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
    private long roleId;
    private int age;
    private LocalDateTime createdAt;

    public User(long userId, String name, long roleId, int age) {
        this.userId = userId;
        this.name = name;
        this.roleId = roleId;
        this.age = age;
        createdAt = LocalDateTime.now();
    }
}
