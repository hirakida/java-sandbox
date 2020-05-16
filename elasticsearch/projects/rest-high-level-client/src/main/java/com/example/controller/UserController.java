package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public List<User> searchByTerm(@PathVariable long userId) {
        return userService.searchByTerm(userId);
    }

    @GetMapping(params = "text")
    public List<User> searchByMultiMatch(@RequestParam String text) {
        return userService.searchByMultiMatch(text);
    }
}
