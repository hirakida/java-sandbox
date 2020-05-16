package com.example.model;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private ObjectId id;
    private long userId;
    private String name;
    private Address address;

    public User(long userId, String name, String city) {
        this.userId = userId;
        this.name = name;
        address = new Address(city);
    }
}
