package com.example;

import static com.example.JesqueConfig.QUEUE_NAME;

import org.springframework.stereotype.Component;

import net.greghaines.jesque.Job;
import net.greghaines.jesque.client.Client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JesqueClient {
    private static final long DELAYED_MILLI_SECONDS = 1000;
    private final Client client;

    public void enqueue(String jobName, String message) {
        long future = System.currentTimeMillis() + DELAYED_MILLI_SECONDS;
        client.delayedEnqueue(QUEUE_NAME, new Job(jobName, message), future);
        log.info("enqueue {} {}", message, future);
    }

    public void enqueue(String jobName, String message, long milliSeconds) {
        long future = System.currentTimeMillis() + milliSeconds;
        client.delayedEnqueue(QUEUE_NAME, new Job(jobName, message), future);
        log.info("enqueue {} {}", message, future);
    }

    public void enqueue(Job job, long milliSeconds) {
        long future = System.currentTimeMillis() + milliSeconds;
        client.delayedEnqueue(QUEUE_NAME, job, future);
        log.info("enqueue {} {}", job, future);
    }

    public void dequeue(Job job) {
        client.removeDelayedEnqueue(QUEUE_NAME, job);
        log.info("dequeue {}", job);
    }
}
