package com.example;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private static final AtomicInteger count = new AtomicInteger(1);
    private final DocumentRepository repository;

    @Scheduled(initialDelay = 5000, fixedRate = 5000)
    public void run() throws InterruptedException {
        final int id = count.getAndIncrement();

        repository.insertOne(id);
        TimeUnit.SECONDS.sleep(1);

        repository.updateOne(id);
        TimeUnit.SECONDS.sleep(1);

        repository.deleteOne(id);
    }
}
