package com.example;

public class BarImpl implements Bar {
    private String message = "Bar";

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }
}
