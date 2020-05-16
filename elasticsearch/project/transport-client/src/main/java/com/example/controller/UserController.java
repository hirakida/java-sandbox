package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(params = "text")
    public List<User> searchByText(@RequestParam String text) {
        return userService.searchByText(text);
    }

    @GetMapping(params = "score")
    public List<User> searchByScore() {
        return userService.searchByScore();
    }
}
