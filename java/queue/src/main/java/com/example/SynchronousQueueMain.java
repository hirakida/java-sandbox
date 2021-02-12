package com.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

public final class SynchronousQueueMain {

    public static void main(String[] args) {
        ExecutorService executor1 = Executors.newSingleThreadExecutor();
        ExecutorService executor2 = Executors.newSingleThreadExecutor();

        SynchronousQueue<Integer> queue = new SynchronousQueue<>();
        executor1.execute(new Consumer(queue));
        executor2.execute(new Producer(queue, 1));
        executor2.execute(new Producer(queue, 2));
        executor2.execute(new Producer(queue, 3));

        executor1.shutdown();
        executor2.shutdown();
    }
}
