package com.example;

import static com.example.Constants.STRING1_TOPIC_NAME;
import static com.example.Constants.STRING2_TOPIC_NAME;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ConsumerApplication {

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new StringKafkaConsumer(STRING1_TOPIC_NAME, "group1", true, false));
        executor.execute(new StringKafkaConsumer(STRING1_TOPIC_NAME, "group2", true, true));
        executor.execute(new StringKafkaConsumer(STRING1_TOPIC_NAME, "group3", false, false));
        executor.execute(new StringKafkaConsumer(STRING2_TOPIC_NAME, "group1", true, false));
        executor.execute(new HelloKafkaConsumer("hello1"));
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
