package com.example;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public void bulkIndex(@RequestParam(defaultValue = "10") int count) {
        userService.bulkIndex(count);
    }

    @DeleteMapping
    public void deleteIndex() {
        userService.deleteIndex();
    }
}
