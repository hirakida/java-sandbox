package com.example.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.mongodb.client.FindIterable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;

    @GetMapping("/users")
    public List<User> findAll() {
        return toList(repository.find());
    }

    @GetMapping("/users/{userId}")
    public List<User> findById(@PathVariable long userId) {
        return toList(repository.find(userId));
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserData userData) {
        repository.insertOne(toUser(userData));
    }

    @PutMapping("/users/{userId}")
    public User update(@PathVariable long userId, @RequestBody UserData userData) {
        return repository.findOneAndUpdate(userId, toUser(userData));
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId) {
        repository.deleteOne(userId);
    }

    private static List<User> toList(FindIterable<User> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    private static User toUser(UserData userData) {
        return new User(userData.getUserId(), userData.getName(), userData.getRoleId(), userData.getAge());
    }

    @Data
    public static class UserData {
        private long userId;
        private String name;
        private long roleId;
        private int age;
    }
}
