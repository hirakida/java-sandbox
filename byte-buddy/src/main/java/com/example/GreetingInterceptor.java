package com.example;

public class GreetingInterceptor {
    public Object greet(Object argument) {
        return "Hello from " + argument;
    }
}
