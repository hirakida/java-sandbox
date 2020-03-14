package com.example.model;

import org.bson.types.ObjectId;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Role {
    private ObjectId id;
    private long roleId;
    private String name;

    public Role(long roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }
}
