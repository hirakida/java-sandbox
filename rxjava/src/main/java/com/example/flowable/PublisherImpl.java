package com.example.flowable;

import java.util.stream.LongStream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublisherImpl implements Publisher<String> {

    @Override
    public void subscribe(Subscriber<? super String> s) {
        log.info("subscribe start");
        SubscriptionImpl subscription = new SubscriptionImpl();

        s.onSubscribe(subscription);

        while (true) {
            long request = subscription.getN();
            if (request == 0) {
                break;
            }

            LongStream.rangeClosed(1, request)
                      .forEach(num -> s.onNext("hello" + num));
        }

        s.onComplete();
        log.info("subscribe end");
    }
}
