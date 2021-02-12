package com.example.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ForkJoinPoolMain {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        int result = pool.invoke(new MyTask(1));
        log.info("invoke: {}", result);

        pool.shutdown();
        try {
            boolean ret = pool.awaitTermination(5, TimeUnit.SECONDS);
            log.info("awaitTermination: {}", ret);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
