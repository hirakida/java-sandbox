package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class);
    private final BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Integer data = queue.poll(1, TimeUnit.SECONDS);
                if (data == null) {
                    break;
                }
                log.info("{}", data);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
        }
    }
}
