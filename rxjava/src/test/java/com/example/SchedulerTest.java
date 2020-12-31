package com.example;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class SchedulerTest {

    @Test
    public void test1() {
        Single.just(1)
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));
    }

    @Test
    public void test2() throws Exception {
        Single.just(2)
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .observeOn(Schedulers.newThread())
              .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void test3() throws Exception {
        Single.just(3)
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .subscribeOn(Schedulers.newThread())
              .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void test4() throws Exception {
        Single.just(4)
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .subscribeOn(Schedulers.newThread())
              .observeOn(Schedulers.newThread())
              .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void test5() throws Exception {
        Single.just(5)
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .subscribeOn(Schedulers.newThread())
              .observeOn(Schedulers.newThread())
              .map(item -> item * 2)
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .observeOn(Schedulers.newThread())
              .subscribe(result -> System.out.println(threadName() + " subscribe: " + result));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    private static String threadName() {
        return Thread.currentThread().getName();
    }
}
