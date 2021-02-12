package com.example.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReentrantReadWriteLockMain {
    private static final Logger log = LoggerFactory.getLogger(ReentrantReadWriteLockMain.class);

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
        private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        @Override
        public void run() {
            IntStream.range(0, 10)
                     .forEach(i -> increment());
        }

        private void increment() {
            lock.writeLock().lock();
            count++;
            lock.writeLock().unlock();

            lock.readLock().lock();
            log.info("count={}", count);
            lock.readLock().unlock();
        }
    }
}
