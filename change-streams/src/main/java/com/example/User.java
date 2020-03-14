package com.example;

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
    private LocalDateTime updatedAt;

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
        updatedAt = LocalDateTime.now();
    }
}
