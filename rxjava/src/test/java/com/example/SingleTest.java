package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleSource;

public class SingleTest {

    @Test
    public void just1() {
        Single.just(LocalTime.now())
              .doOnSuccess(item -> System.out.println(threadName() + " doOnSuccess: " + item))
              .doAfterTerminate(() -> System.out.println(threadName() + " doAfterTerminate"))
              .doAfterSuccess(item -> System.out.println(threadName() + " doAfterSuccess: " + item))
              .subscribe();
    }

    @Test
    public void just2() {
        Single<LocalTime> single = Single.just(LocalTime.now());
        assertEquals(single.blockingGet(), single.blockingGet());
    }

    @Test
    public void create() {
        final Single<LocalTime> single = Single.create(emitter -> emitter.onSuccess(LocalTime.now()));
        assertNotEquals(single.blockingGet(), single.blockingGet());
    }

    @Test
    public void fromCallable() {
        Single<LocalTime> single = Single.fromCallable(LocalTime::now);
        assertNotEquals(single.blockingGet(), single.blockingGet());
    }

    @Test
    public void defer() {
        SingleSource<LocalTime> source = observer -> observer.onSuccess(LocalTime.now());
        Single<LocalTime> single = Single.defer(() -> source);
        assertNotEquals(single.blockingGet(), single.blockingGet());
    }

    @Test
    public void error1() {
        Single.error(new RuntimeException("Single.error"))
              .doOnError(e -> System.out.println(threadName() + " doOnError: " + e))
              .test()
              .assertError(RuntimeException.class);
    }

    @Test
    public void error2() {
        Single.error(new RuntimeException("Single.error"))
              .doOnError(e -> System.out.println(threadName() + " doOnError: " + e))
              .onErrorReturnItem("error item")
              .test()
              .assertValue("error item");
    }

    @Test
    public void map() {
        Single.just(1)
              .map(item -> item * 2)
              .test()
              .assertValue(2);
    }

    @Test
    public void flatmap() {
        Single.just(2)
              .flatMap(item -> Single.just(item * 2))
              .test()
              .assertValue(4);
    }

    @Test
    public void merge() {
        Flowable<Integer> flowable = Single.merge(Single.just(1),
                                                  Single.just(2),
                                                  Single.just(3),
                                                  Single.just(4));
        flowable.test()
                .assertValues(1, 2, 3, 4);
    }

    @Test
    public void zip() {
        Single<Integer> single = Single.zip(Single.just(1),
                                            Single.just(2),
                                            Integer::sum);
        single.test()
              .assertValue(3);
    }

    private static String threadName() {
        return Thread.currentThread().getName();
    }
}
