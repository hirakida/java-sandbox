package com.example;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private ObjectId id;
    private long userId;
    private String name;

    public User(long userId, String name) {
        this.userId = userId;
        this.name = name;
    }
}
