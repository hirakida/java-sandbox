package com.example;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(Producer.class);
    private final BlockingQueue<Integer> queue;
    private final int element;

    public Producer(BlockingQueue<Integer> queue, int element) {
        this.queue = queue;
        this.element = element;
    }

    @Override
    public void run() {
        try {
            queue.put(element);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
        log.info("run [{}]", element);
    }
}
