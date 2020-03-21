package com.example;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.User;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping
    public Flux<User> findAll() {
        return Flux.from(userRepository.find());
    }

    @GetMapping("/{userId}")
    public Flux<User> findByUserId(@PathVariable long userId) {
        return Flux.from(userRepository.find(userId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> create(@RequestBody @Validated Request request) {
        return Flux.from(userRepository.insertOne(request.toUser()))
                   .then();
    }

    @Data
    public static class Request {
        @NotNull
        private Long userId;
        @NotEmpty
        private String name;
        @NotEmpty
        private String city;

        public User toUser() {
            return new User(userId, name, city);
        }
    }
}
