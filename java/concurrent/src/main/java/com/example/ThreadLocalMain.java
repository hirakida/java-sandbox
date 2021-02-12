package com.example;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ThreadLocalMain {
    private static final Logger log = LoggerFactory.getLogger(ThreadLocalMain.class);

    public static void main(String[] args) {
        RunnableImpl runnable = new RunnableImpl();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();
    }

    private static class RunnableImpl implements Runnable {
        private final ThreadLocal<Integer> count = new ThreadLocal<>();

        @Override
        public void run() {
            IntStream.range(0, 100)
                     .forEach(i -> increment());
            log.info("count={}", count.get());
        }

        private void increment() {
            int value = Optional.ofNullable(count.get())
                                .orElse(0);
            count.set(value + 1);
        }
    }
}
