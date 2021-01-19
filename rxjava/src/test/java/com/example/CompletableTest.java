package com.example;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.reactivex.Completable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public void merge() {
        Completable.merge(List.of(Completable.fromRunnable(() -> log.info("run1")),
                                  Completable.fromRunnable(() -> log.info("run2"))))
                   .andThen(Completable.fromRunnable(() -> log.info("run3")))
                   .subscribe(() -> log.info("subscribe"));
    }

    @Test
    public void mergeArray() {
        Completable.mergeArray(Completable.fromRunnable(() -> log.info("run1"))
                                          .subscribeOn(Schedulers.newThread()),
                               Completable.fromRunnable(() -> log.info("run2")))
                   .andThen(Completable.fromRunnable(() -> log.info("run3")))
                   .subscribe(() -> log.info("subscribe"));
    }

    @Test
    public void concat() {
        Completable.concat(List.of(Completable.fromAction(() -> log.info("action1")),
                                   Completable.fromAction(() -> log.info("action2"))))
                   .andThen(Completable.fromAction(() -> log.info("action3")))
                   .subscribe(() -> log.info("subscribe"));
    }

    @Test
    public void concatArray() {
        Completable.concatArray(Completable.fromAction(() -> log.info("action1"))
                                           .subscribeOn(Schedulers.newThread()),
                                Completable.fromAction(() -> log.info("action2")))
                   .andThen(Completable.fromAction(() -> log.info("action3")))
                   .subscribe(() -> log.info("subscribe"));
    }
}
