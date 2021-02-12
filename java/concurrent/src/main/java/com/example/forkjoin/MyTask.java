package com.example.forkjoin;

import java.util.concurrent.RecursiveTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("serial")
public class MyTask extends RecursiveTask<Integer> {
    private final int n;

    @Override
    public Integer compute() {
        if (n > 3) {
            return n;
        }
        log.info("compute start: {}", n);

        MyTask task1 = new MyTask(n + 1);
        MyTask task2 = new MyTask(n + 1);
        invokeAll(task1, task2);
        int i1 = task1.join();
        int i2 = task2.join();

        log.info("compute end: {} {}", i1, i2);
        return i1 + i2;
    }
}
