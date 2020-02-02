package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.config.ChangeStreamListener;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationEvent {
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final DocumentRepository repository;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        repository.drop();
        repository.createIndex();

        executorService.execute(() -> {
            ChangeStreamListener listener = new ChangeStreamListener();
            listener.watch(repository.getCollection());
        });
    }

    @PreDestroy
    public void shutdown() {
        executorService.shutdown();
    }
}
