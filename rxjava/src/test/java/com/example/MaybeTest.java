package com.example;

import org.junit.jupiter.api.Test;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class MaybeTest {

    @Test
    public void just1() {
        Maybe.just(1)
             .compose(value -> value.defaultIfEmpty(10))
             .test()
             .assertValue(1);
    }

    @Test
    public void just2() {
        Maybe.just(2)
             .doOnSubscribe(item -> System.out.println("doOnSubscribe"))
             .doOnSuccess(item -> System.out.println("doOnSuccess: " + item))
             .doOnComplete(() -> System.out.println("doOnComplete"))
             .subscribe(item -> System.out.println("onSuccess: " + item),
                        e -> System.out.println("onError: " + e),
                        () -> System.out.println("onComplete"));
    }

    @Test
    public void empty1() {
        Maybe.empty()
             .compose(value -> value.defaultIfEmpty(10))
             .test()
             .assertValue(10);
    }

    @Test
    public void empty2() {
        Maybe.empty()
             .doOnComplete(() -> System.out.println("doOnComplete"))
             .subscribe(item -> System.out.println("onSuccess: " + item),
                        e -> System.out.println("onError: " + e),
                        () -> System.out.println("onComplete"));
    }

    @Test
    public void create() {
        Maybe.create(emitter -> emitter.onSuccess(1))
             .test()
             .assertValue(1);
    }

    @Test
    public void map() {
        Maybe.just(1)
             .map(item -> item * 2)
             .test()
             .assertValue(2);
    }

    @Test
    public void flatmap() {
        Maybe.just(1)
             .flatMap(item -> Maybe.just(item * 2))
             .test()
             .assertValue(2);
    }

    @Test
    public void toSingle() {
        Single<Integer> single1 = Maybe.just(1)
                                       .toSingle();
        single1.test()
               .assertValue(1);

        Single<Object> single2 = Maybe.create(emitter -> emitter.onSuccess(2))
                                      .toSingle(0);
        single2.test()
               .assertValue(2);

        Single<Object> single3 = Maybe.empty()
                                      .toSingle(0);
        single3.test()
               .assertValue(0);
    }

    @Test
    public void flatMapSingle() {
        Maybe.just(1)
             .flatMapSingle(item -> Single.just(item * 2))
             .test()
             .assertValue(2);
    }

    @Test
    public void flatMapSingleElement() {
        Maybe<Integer> maybe1 = Maybe.just(1)
                                     .flatMapSingleElement(item -> Single.just(item * 2));
        maybe1.test()
              .assertValue(2);

        Single<Object> single1 = Maybe.empty()
                                      .flatMapSingleElement(Single::just)
                                      .switchIfEmpty(Single.just(0));
        single1.test()
               .assertValue(0);
    }
}
