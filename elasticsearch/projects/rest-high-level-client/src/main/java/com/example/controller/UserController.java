package com.example.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Company;
import com.example.model.User;
import com.example.service.UserService;

import lombok.Data;
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

    @GetMapping(params = "query=score")
    public List<User> searchByScore() {
        return userService.searchByScore();
    }

    @GetMapping(params = "score")
    public List<User> searchByScore(@RequestParam double score) {
        return userService.searchByScore(score);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable long userId, @RequestBody @Validated UserRequest request) {
        userService.update(request.toUser(userId));
    }

    @Data
    public static class UserRequest {
        @NotNull
        private String name;
        @NotNull
        private String email;
        @NotNull
        private Double score;

        public User toUser(long userId) {
            return new User(userId, name, email, score);
        }
    }
}
