package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationEventListener {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final UserRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        repository.init();
        executorService.execute(new ChangeStreamListener<>(repository.getCollection()));
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
