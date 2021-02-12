package com.example.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReentrantLockMain {
    private static final Logger log = LoggerFactory.getLogger(ReentrantLockMain.class);

    public static void main(String[] args) {
        RunnableImpl runnable = new RunnableImpl();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();
    }

    private static class RunnableImpl implements Runnable {
        private int count;
        private final ReentrantLock lock = new ReentrantLock();

        @Override
        public void run() {
            IntStream.range(0, 10)
                     .forEach(i -> increment());
        }

        private void increment() {
            try {
                while (!lock.tryLock(10, TimeUnit.MILLISECONDS)) {
                    Thread.sleep(10);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }

            count++;
            if (lock.isHeldByCurrentThread()) {
                log.info("count={}", count);
                lock.unlock();
            }
        }
    }
}
