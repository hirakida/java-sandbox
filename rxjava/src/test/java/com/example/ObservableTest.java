package com.example;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import io.reactivex.Observable;

public class ObservableTest {

    @Test
    public void just() {
        Observable.just(1, 2, 3)
                  .doOnNext(item -> System.out.println("doOnNext: " + item))
                  .doAfterNext(item -> System.out.println("doAfterNext: " + item))
                  .doOnComplete(() -> System.out.println("doOnComplete"))
                  .doAfterTerminate(() -> System.out.println("doAfterTerminate"))
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));
    }

    @Test
    public void fromIterable() {
        Observable.fromIterable(List.of(1, 2, 3))
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));
    }

    @Test
    public void interval() throws Exception {
        Observable.interval(10, TimeUnit.MILLISECONDS)
                  .take(5)
                  .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                             e -> System.out.println(threadName() + " onError"),
                             () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void timer() throws Exception {
        Observable.timer(10, TimeUnit.MILLISECONDS)
                  .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                             e -> System.out.println(threadName() + " onError"),
                             () -> System.out.println(threadName() + " onComplete"));

        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void defer() {
        Observable.defer(() -> Observable.just(1, 2, 3))
                  .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                             e -> System.out.println(threadName() + " onError"),
                             () -> System.out.println(threadName() + " onComplete"));
    }

    @Test
    public void zip() {
        Observable.zip(Observable.just(1, 2, 3),
                       Observable.just(1, 2, 3, 4),
                       Integer::sum)
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));
    }

    @Test
    public void flatMap() {
        Observable.just(List.of(1, 2, 3), List.of(4, 5, 6))
                  .flatMap(Observable::fromIterable)
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));
    }

    @Test
    public void error() {
        // onErrorReturnItem
        Observable.error(new RuntimeException("onErrorReturnItem"))
                  .onErrorReturnItem("error1")
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));

        // onErrorReturn
        Observable.error(new RuntimeException())
                  .onErrorReturn(e -> "error2")
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));

        // onErrorResumeNext
        Observable.error(new RuntimeException())
                  .onErrorResumeNext(Observable.just("error3"))
                  .subscribe(item -> System.out.println("onNext: " + item),
                             e -> System.out.println("onError"),
                             () -> System.out.println("onComplete"));
    }

    @Test
    public void retry() {
        Observable.error(new RuntimeException("retry"))
                  .doOnSubscribe(onSubscribe -> System.out.println(threadName() + " doOnSubscribe"))
                  .retry(3)
                  .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                             e -> System.out.println(threadName() + " onError"),
                             () -> System.out.println(threadName() + " onComplete"));
    }

    @Test
    public void retryUntil() {
        AtomicInteger count = new AtomicInteger(0);
        Observable.error(new RuntimeException("retry"))
                  .doOnSubscribe(onSubscribe -> System.out.println(threadName() + " doOnSubscribe"))
                  .retryUntil(() -> count.getAndIncrement() > 10)
                  .subscribe(item -> System.out.println(threadName() + " onNext: " + item),
                             e -> System.out.println(threadName() + " onError"),
                             () -> System.out.println(threadName() + " onComplete"));
    }

    private static String threadName() {
        return Thread.currentThread().getName();
    }
}
