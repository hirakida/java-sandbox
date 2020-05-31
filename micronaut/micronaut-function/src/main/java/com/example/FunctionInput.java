package com.example;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class FunctionInput {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
