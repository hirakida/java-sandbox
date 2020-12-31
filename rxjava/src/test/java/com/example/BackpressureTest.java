package com.example;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class BackpressureTest {

    @Test
    public void bufferSizeDefault() throws Exception {
        Flowable.range(1, 5)
                .doOnEach(item -> System.out.println(threadName() + " doOnEach: " + item))
                .observeOn(Schedulers.newThread())
                .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void bufferSize1() throws Exception {
        Flowable.range(1, 5)
                .doOnEach(item -> System.out.println(threadName() + " doOnEach: " + item))
                .observeOn(Schedulers.newThread(), false, 1)
                .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void backpressureBuffer() throws Exception {
        Flowable.range(1, 5)
                .doOnEach(item -> System.out.println(threadName() + " doOnEach: " + item))
                .onBackpressureBuffer()
                .observeOn(Schedulers.newThread(), false, 1)
                .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void backpressureDrop() throws Exception {
        Flowable.range(1, 5)
                .doOnEach(item -> System.out.println(threadName() + " doOnEach: " + item))
                .onBackpressureDrop()
                .observeOn(Schedulers.newThread(), false, 1)
                .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void backpressureLatest() throws Exception {
        Flowable.range(1, 5)
                .doOnEach(item -> System.out.println(threadName() + " doOnEach: " + item))
                .onBackpressureLatest()
                .observeOn(Schedulers.newThread(), false, 1)
                .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void window() throws Exception {
        Flowable.range(1, 5)
                .doOnEach(item -> System.out.println(threadName() + " doOnEach: " + item))
                .window(3, 3, 5)
                .observeOn(Schedulers.newThread(), false, 1)
                .subscribe(result -> System.out.println(threadName() + " subscribe: "
                                                        + result.toList().blockingGet()));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    private static String threadName() {
        return Thread.currentThread().getName();
    }
}
