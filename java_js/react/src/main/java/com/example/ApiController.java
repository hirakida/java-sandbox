package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class ApiController {

    @GetMapping("/api/message")
    public Message message() {
        return new Message("Hello React!");
    }

    @Data
    @AllArgsConstructor
    public static class Message {
        private String text;
    }
}
