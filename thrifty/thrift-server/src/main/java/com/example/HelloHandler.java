package com.example;

import org.springframework.stereotype.Component;

@Component
public class HelloHandler implements Hello.Iface {
    @Override
    public String hello() {
        return "Hello!";
    }
}
