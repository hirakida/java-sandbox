package com.example.forkjoin;

import java.util.concurrent.RecursiveAction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("serial")
public class MyAction extends RecursiveAction {
    private final int n;

    @Override
    public void compute() {
        if (n > 3) {
            return;
        }
        log.info("compute start: {}", n);

        MyAction action1 = new MyAction(n + 1);
        MyAction action2 = new MyAction(n + 1);
        invokeAll(action1, action2);
        action1.join();
        action2.join();

        log.info("compute end: {}", n);
    }
}
