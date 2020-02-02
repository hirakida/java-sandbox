package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.UserRepository;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/users")
    public Flux<User> findAll() {
        return Flux.from(userRepository.find());
    }

    @GetMapping("/users/{userId}")
    public Flux<User> findById(@PathVariable long userId) {
        return Flux.from(userRepository.find(userId));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody UserData userData) {
        User user = new User(userData.getUserId(), userData.getName());
        return Flux.from(userRepository.insertOne(user)).then();
    }

    @Data
    public static class UserData {
        private long userId;
        private String name;
    }
}
