package com.example;

import java.time.Instant;
import java.util.stream.IntStream;

import org.bson.Document;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {
    private final CappedRepository cappedRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        IntStream.rangeClosed(1, 10)
                 .mapToObj(i -> {
                     Document document = new Document();
                     document.append("timestamp", Instant.now().toEpochMilli());
                     document.append("value", "value" + i);
                     return document;
                 }).forEach(cappedRepository::insertOne);

        log.info("countDocuments: {}", cappedRepository.countDocuments());
        for (Document document : cappedRepository.find()) {
            log.info("{}", document);
        }
    }
}
