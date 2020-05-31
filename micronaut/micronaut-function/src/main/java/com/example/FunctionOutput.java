package com.example;

import io.micronaut.core.annotation.Introspected;

@Introspected
public class FunctionOutput {
    private final String name;

    public FunctionOutput(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
