package com.example;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class ApplicationEvent {
    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        List<User> users = IntStream.rangeClosed(1, 10)
                                    .mapToObj(i -> new User(i, "name" + i))
                                    .collect(toList());

        Flux.from(userRepository.drop())
            .thenMany(userRepository.createIndex())
            .thenMany(userRepository.insertMany(users))
            .thenMany(userRepository.find())
            .subscribe(new SubscriberImpl<>());
    }
}
