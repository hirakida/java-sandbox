package com.example;

import org.bson.Document;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriberImpl<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {
        s.request(Integer.MAX_VALUE);
    }

    @Override
    public void onNext(final T t) {
        if (t instanceof Document) {
            log.info("onNext: {}", ((Document) t).toJson());
        } else {
            log.info("onNext: {}", t);
        }
    }

    @Override
    public void onError(final Throwable t) {
        log.error("onError: {}", t.getMessage(), t);
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
