package com.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CountDownLatchMain {
    private static final Logger log = LoggerFactory.getLogger(CountDownLatchMain.class);

    public static void main(String[] args) throws Exception {
        RunnableImpl runnable = new RunnableImpl();
        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);
        executor.shutdown();
    }

    public static class RunnableImpl implements Runnable {
        private final CountDownLatch countDownLatch = new CountDownLatch(30);

        @Override
        public void run() {
            log.info("run start");
            IntStream.range(0, 10)
                     .forEach(i -> countDown());

            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
            log.info("run end");
        }

        private void countDown() {
            countDownLatch.countDown();
            log.info("countDown={}", countDownLatch.getCount());
        }
    }
}
