package com.example.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SemaphoreMain {
    private static final Logger log = LoggerFactory.getLogger(SemaphoreMain.class);

    public static void main(String[] args) {
        RunnableImpl runnable = new RunnableImpl();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();
    }

    public static class RunnableImpl implements Runnable {
        private int count;
        private final Semaphore semaphore = new Semaphore(1);

        @Override
        public void run() {
            IntStream.range(0, 100)
                     .forEach(i -> increment());
        }

        private void increment() {
            try {
                semaphore.acquire();
                count++;
                log.info("count={}", count);
            } catch (InterruptedException e) {
                log.error(e.toString());
            } finally {
                semaphore.release();
            }
        }
    }
}
