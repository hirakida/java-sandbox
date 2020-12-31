package com.example.single;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleObserverImpl implements SingleObserver<Object> {
    @Override
    public void onSubscribe(Disposable disposable) {
        log.info("onSubscribe: isDisposed={}", disposable.isDisposed());
    }

    @Override
    public void onSuccess(Object value) {
        log.info("onSuccess: {}", value);
    }

    @Override
    public void onError(Throwable e) {
                log.info("onError: ", e);
    }
}
