package com.example.observable;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObserverImpl implements Observer<Object> {
    @Override
    public void onSubscribe(Disposable disposable) {
        log.info("onSubscribe");
    }

    @Override
    public void onNext(Object item) {
        log.info("onNext: item={}", item);
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }

    @Override
    public void onError(Throwable e) {
        log.error("onError: ", e);
    }
}
