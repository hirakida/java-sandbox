package com.example.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ScheduledExecutorServiceMain {
    private static final Logger log = LoggerFactory.getLogger(ScheduledExecutorServiceMain.class);

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(6);

        executor.execute(new RunnableImpl());
        executor.execute(new RunnableImpl());
        executor.execute(() -> log.info("Runnable::run 3"));
        executor.schedule(new RunnableImpl(), 1, TimeUnit.SECONDS);
        submit(executor);

        executor.shutdown();
    }

    private static void submit(ScheduledExecutorService executor) {
        // Runnable
        Future<?> future1 = executor.submit(() -> log.info("Runnable::run submit1"));
        Future<Integer> future2 = executor.submit(() -> log.info("Runnable::run submit2"), 1);
        Future<Boolean> future3 = executor.submit(() -> log.info("Runnable::run submit3"), true);

        // Callable
        Future<Integer> future4 = executor.submit(() -> {
            log.info("Callable::call 1");
            return 4;
        });
        Future<Integer> future5 = executor.submit(() -> {
            log.info("Callable::call 2");
            return 5;
        });

        try {
            log.info("Runnable future1: {}", future1.get());
            log.info("Runnable future2: {}", future2.get());
            log.info("Runnable future3: {}", future3.get());
            log.info("Callable future4: {}", future4.get());
            log.info("Callable future5: {}", future5.get());
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        }
    }

    private static class RunnableImpl implements Runnable {
        @Override
        public void run() {
            log.info("Runnable::run");
            IntStream.range(0, 10)
                     .forEach(i -> {
                         try {
                             threadInfo();
                             Thread.sleep(100);
                             threadInfo();
                         } catch (InterruptedException e) {
                             log.error(e.getMessage());
                         }
                     });
        }

        private static void threadInfo() {
            Thread thread = Thread.currentThread();
            log.info("name=" + thread.getThreadGroup() + " state=" + thread.getState());
        }
    }
}
