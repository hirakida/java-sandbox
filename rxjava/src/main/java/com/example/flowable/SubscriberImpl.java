package com.example.flowable;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubscriberImpl implements Subscriber<String> {
    private SubscriptionImpl subscription;
    private long requestCount;

    @Override
    public void onSubscribe(Subscription subscription) {
        this.subscription = (SubscriptionImpl) subscription;

        this.subscription.request(1);
        log.info("onSubscribe: {}", subscription);
    }

    @Override
    public void onNext(String t) {
        requestCount++;
        log.info("onNext: {}", t);
        if (requestCount == subscription.getN()) {
            long nextRequest = requestCount == 5 ? 0 : requestCount + 1;
            subscription.request(nextRequest);
            requestCount = 0;
        }
    }

    @Override
    public void onError(Throwable t) {
        log.info("onError: ", t);
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
