package com.example.flowable;

import org.reactivestreams.Subscription;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriptionImpl implements Subscription {
    @Getter
    private long n;

    @Override
    public void request(long n) {
        this.n = n;
        log.info("request: {}", n);
    }

    @Override
    public void cancel() {
        log.info("cancel");
    }
}
