package com.example;

import org.junit.jupiter.api.Test;

import io.reactivex.Completable;

public class CompletableTest {

    @Test
    public void create() {
        Completable.create(emitter -> {
            System.out.println("create");
            emitter.onComplete();
        })
                   .subscribe(() -> System.out.println("onComplete"),
                              e -> System.out.println("onError: " + e));
    }

    @Test
    public void defer() {
        Completable.defer(() -> observer -> {
            System.out.println("defer");
            observer.onComplete();
        })
                   .subscribe(() -> System.out.println("onComplete"));
    }

    @Test
    public void fromAction() {
        Completable.fromAction(() -> System.out.println("fromAction"))
                   .doOnComplete(() -> System.out.println("doOnComplete"))
                   .doOnTerminate(() -> System.out.println("doOnTerminate"))
                   .subscribe();
    }

    @Test
    public void error() {
        Completable.create(emitter -> emitter.onError(new RuntimeException()))
                   .doOnError(e -> System.out.println("doOnError: " + e))
                   .subscribe(() -> System.out.println("onComplete"),
                              e -> System.out.println("onError: " + e));
    }

    @Test
    public void andThen() {
        Completable.fromAction(() -> System.out.println("fromAction1"))
                   .andThen(Completable.fromAction(() -> System.out.println("fromAction2")))
                   .subscribe();
    }

    @Test
    public void mergeArray() {
        Completable.mergeArray(Completable.fromAction(() -> System.out.println("fromAction1")),
                               Completable.fromAction(() -> System.out.println("fromAction2")))
                   .andThen(Completable.fromAction(() -> System.out.println("fromAction3")))
                   .subscribe();
    }
}
