package com.example.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ForkJoinTaskMain {

    public static void main(String[] args) {
        ForkJoinTask<Integer> task = ForkJoinTask.adapt(() -> {
            int ret = new Random().nextInt();
            log.info("adapt: {}", ret);
            return ret;
        });

        int result = task.fork().join();
        log.info("join: {}", result);
    }
}
