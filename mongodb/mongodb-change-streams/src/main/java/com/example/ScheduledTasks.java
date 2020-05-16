package com.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.model.User;
import com.example.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {
    private static final AtomicInteger COUNT = new AtomicInteger(1);
    private final UserRepository repository;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void run() throws InterruptedException {
        final int id = COUNT.getAndIncrement();
        User user = new User(id, "name" + id);

        repository.insertOne(user);
        TimeUnit.SECONDS.sleep(1);

        user.setName(user.getName() + id);
        repository.updateOne(user);
        TimeUnit.SECONDS.sleep(1);

        repository.deleteOne(id);
    }
}
