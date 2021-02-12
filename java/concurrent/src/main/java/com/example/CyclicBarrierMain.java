package com.example;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CyclicBarrierMain {
    private static final Logger log = LoggerFactory.getLogger(CyclicBarrierMain.class);

    public static void main(String[] args) throws Exception {
        RunnableImpl runnable = new RunnableImpl();
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.execute(runnable);
        TimeUnit.SECONDS.sleep(1);
        executor.execute(runnable);
        TimeUnit.SECONDS.sleep(1);
        executor.execute(runnable);

        executor.shutdown();
    }

    public static class RunnableImpl implements Runnable {
        private final CyclicBarrier barrier = new CyclicBarrier(3, () -> log.info("barrierAction"));

        @Override
        public void run() {
            log.info("run start");
            try {
                TimeUnit.SECONDS.sleep(5);
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                log.error(e.getMessage(), e);
            }
            log.info("run end");
        }
    }
}
