package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SynchronizedMain {
    private static final Logger log = LoggerFactory.getLogger(SynchronizedMain.class);

    public static void main(String[] args) {
        RunnableImpl runnable = new RunnableImpl();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();
    }

    public static class RunnableImpl implements Runnable {
        private int count1;
        private int count2;

        @Override
        public void run() {
            IntStream.range(0, 100)
                     .forEach(i -> increment());
        }

        private void increment() {
            count1 += 1;

            synchronized (this) {
                count2 += 1;
                log.info("count1={} count2={}", count1, count2);
            }
        }
    }
}
