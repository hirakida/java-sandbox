package com.example.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CompletableObserverImpl implements CompletableObserver {
    private final String value;

    @Override
    public void onSubscribe(Disposable disposable) {
        log.info("onSubscribe: {}", value);
    }

    @Override
    public void onError(Throwable e) {
        log.info("onError: ", e);
    }

    @Override
    public void onComplete() {
        log.info("onComplete: {}", value);
    }
}
