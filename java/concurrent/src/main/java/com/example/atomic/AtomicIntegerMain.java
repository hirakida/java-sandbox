package com.example.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AtomicIntegerMain {
    private static final Logger log = LoggerFactory.getLogger(AtomicIntegerMain.class);

    public static void main(String[] args) {
        RunnableImpl runnable = new RunnableImpl();
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        Thread t3 = new Thread(runnable);

        try {
            t1.start();
            t2.start();
            t3.start();
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }

        log.info("count1={} count2={}", runnable.count1, runnable.count2);
    }

    private static class RunnableImpl implements Runnable {
        private int count1;
        private final AtomicInteger count2 = new AtomicInteger(0);

        @Override
        public void run() {
            IntStream.range(0, 10)
                     .forEach(i -> increment());
        }

        private void increment() {
            int c1 = ++count1;
            int c2 = count2.incrementAndGet();
            log.info("count1={} count2={}", c1, c2);
        }
    }
}
