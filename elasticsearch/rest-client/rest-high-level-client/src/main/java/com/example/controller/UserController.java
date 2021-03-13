package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PostMapping
    public void bulkIndex(@RequestParam(defaultValue = "10") int count) {
        userService.bulkIndex(count);
    }

    @DeleteMapping
    public void deleteIndex() {
        userService.deleteIndex();
    }

    @GetMapping
    public List<User> searchByMatchAll() {
        return userService.searchByMatchAll();
    }

    @GetMapping("/{userId}")
    public List<User> searchByTerm(@PathVariable long userId) {
        return userService.searchByTerm(userId);
    }

    @GetMapping(params = { "from", "to" })
    public List<User> searchByRange(@RequestParam long from, @RequestParam long to) {
        return userService.searchByRange(from, to);
    }

    @GetMapping(params = "text")
    public List<User> searchByMultiMatch(@RequestParam String text) {
        return userService.searchByMultiMatch(text);
    }

    @GetMapping(params = "query=bool")
    public List<User> searchByBool(@RequestParam String text) {
        return userService.searchByBool(text);
    }

    @GetMapping(params = "query=score")
    public List<User> searchByScore() {
        return userService.searchByScore();
    }

    @GetMapping(params = "age")
    public List<User> searchByScript(@RequestParam int age) {
        return userService.searchByScript(age);
    }

    @PutMapping("/{userId}")
    public void update(@PathVariable long userId, @RequestParam double score) {
        userService.update(userId, score);
    }
}
