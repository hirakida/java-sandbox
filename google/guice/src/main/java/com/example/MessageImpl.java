package com.example;

public class MessageImpl implements Message {
    @Override
    public void hello() {
        System.out.println("Hello Guice!");
    }
}
