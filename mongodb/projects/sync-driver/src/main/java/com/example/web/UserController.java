package com.example.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.mongodb.client.FindIterable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository repository;

    @GetMapping
    public List<User> findAll() {
        return toList(repository.find());
    }

    @GetMapping("/{userId}")
    public List<User> findById(@PathVariable long userId) {
        return toList(repository.find(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody UserRequest request) {
        repository.insertOne(request.toUser());
    }

    @PutMapping("/{userId}")
    public User update(@PathVariable long userId, @RequestBody UserRequest request) {
        return repository.findOneAndUpdate(userId, request.toUser());
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long userId) {
        repository.deleteOne(userId);
    }

    private static List<User> toList(FindIterable<User> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false)
                            .collect(Collectors.toList());
    }

    @Data
    public static class UserRequest {
        private long userId;
        private String name;
        private int age;

        public User toUser() {
            return new User(userId, name, age);
        }
    }
}
