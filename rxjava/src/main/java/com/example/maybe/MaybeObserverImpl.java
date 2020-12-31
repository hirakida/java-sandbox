package com.example.maybe;

import io.reactivex.MaybeObserver;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaybeObserverImpl implements MaybeObserver<Object> {
    @Override
    public void onSubscribe(Disposable disposable) {
        log.info("onSubscribe: isDisposed={}", disposable.isDisposed());
    }

    @Override
    public void onSuccess(Object value) {
        log.info("onSuccess: value={}", value);
    }

    @Override
    public void onError(Throwable e) {
        log.info("onError: ", e);
    }

    @Override
    public void onComplete() {
        log.info("onComplete");
    }
}
