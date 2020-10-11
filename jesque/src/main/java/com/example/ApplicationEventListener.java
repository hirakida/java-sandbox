package com.example;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import net.greghaines.jesque.Job;

import com.example.jobs.Job1;
import com.example.jobs.Job2;
import com.example.jobs.Job3;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationEventListener {
    private final JesqueClient jesqueClient;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        jesqueClient.enqueue(Job1.class.getSimpleName(), "test1", 1000);
        jesqueClient.enqueue(Job2.class.getSimpleName(), "test2", 1000);
        jesqueClient.enqueue(Job3.class.getSimpleName(), "test3", 1000);

        jesqueClient.enqueue(Job1.class.getSimpleName(), "test4", 3000);
        jesqueClient.enqueue(Job1.class.getSimpleName(), "test4", 3000);

        Job job = new Job("Job1", "test5");
        jesqueClient.enqueue(job, 3000);
        jesqueClient.dequeue(job);
    }
}
