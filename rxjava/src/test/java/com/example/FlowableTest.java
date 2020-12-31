package com.example;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.schedulers.Schedulers;

public class FlowableTest {

    @Test
    public void just() {
        Flowable.just(1, 2, 3)
                .test()
                .assertValues(1, 2, 3);
    }

    @Test
    public void generate() {
        Flowable.generate(emitter -> emitter.onNext(Math.random()))
                .limit(5)
                .cast(Double.class)
                .subscribe(item -> System.out.println("onNext: " + item),
                           e -> System.out.println("onError"),
                           () -> System.out.println("onComplete"));
    }

    @Test
    public void merge() throws Exception {
        Flowable.merge(Flowable.range(1, 5)
                               .delay(10, TimeUnit.MILLISECONDS),
                       Flowable.range(6, 5)
                               .delay(10, TimeUnit.MILLISECONDS))
                .subscribe(item -> System.out.println("onNext: " + item),
                           e -> System.out.println("onError"),
                           () -> System.out.println("onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void zip() {
        Flowable.zip(Flowable.range(1, 5),
                     Flowable.range(1, 5),
                     Integer::sum)
                .subscribe(item -> System.out.println("onNext: " + item),
                           e -> System.out.println("onError"),
                           () -> System.out.println("onComplete"));
    }

    @Test
    public void startWith() {
        Flowable.range(6, 5)
                .startWith(Flowable.range(1, 5))
                .subscribe(item -> System.out.println("onNext: " + item),
                           e -> System.out.println("onError"),
                           () -> System.out.println("onComplete"));
    }

    @Test
    public void concat() throws Exception {
        Flowable.concat(Flowable.range(1, 5)
                                .subscribeOn(Schedulers.newThread())
                                .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)),
                        Flowable.range(6, 5)
                                .subscribeOn(Schedulers.newThread())
                                .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)))
                .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                           e -> System.out.println(threadName() + " onError"),
                           () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void concatEager() throws Exception {
        Flowable.concatEager(
                List.of(Flowable.range(1, 5)
                                .subscribeOn(Schedulers.newThread())
                                .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)),
                        Flowable.range(6, 5)
                                .subscribeOn(Schedulers.newThread())
                                .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i))))
                .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                           e -> System.out.println(threadName() + " onError"),
                           () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void concatArrayEager() throws Exception {
        Flowable.concatArrayEager(Flowable.range(1, 5)
                                          .subscribeOn(Schedulers.newThread())
                                          .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)),
                                  Flowable.range(6, 5)
                                          .subscribeOn(Schedulers.newThread())
                                          .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)))
                .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                           e -> System.out.println(threadName() + " onError"),
                           () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void flatmap() throws Exception {
        Flowable.range(1, 5)
                .flatMap(item -> Flowable.just(item)
                                         .subscribeOn(Schedulers.newThread())
                                         .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)))
                .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                           e -> System.out.println(threadName() + " onError"),
                           () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void concatMap() throws Exception {
        Flowable.range(1, 5)
                .concatMap(item -> Flowable.just(item)
                                           .subscribeOn(Schedulers.newThread())
                                           .doOnNext(i -> System.out.println(threadName() + " doOnNext: " + i)))
                .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                           e -> System.out.println(threadName() + " onError"),
                           () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void concatMapEager() throws Exception {
        Flowable.range(1, 5)
                .concatMapEager(item -> Flowable.just(item)
                                                .subscribeOn(Schedulers.newThread())
                                                .doOnNext(i -> System.out.println(threadName()
                                                                                  + " doOnNext: " + i)))
                .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                           e -> System.out.println(threadName() + " onError"),
                           () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void connectableFlowable() throws Exception {
        final ConnectableFlowable<Long> flowable = Flowable.interval(10, TimeUnit.MILLISECONDS)
                                                           .take(10)
                                                           .publish();
        flowable.connect();

        flowable.subscribe(item -> System.out.println(threadName() + " onNext1: " + item));
        TimeUnit.MILLISECONDS.sleep(50);
        flowable.subscribe(item -> System.out.println(threadName() + " onNext2: " + item));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    private static String threadName() {
        return Thread.currentThread().getName();
    }
}
