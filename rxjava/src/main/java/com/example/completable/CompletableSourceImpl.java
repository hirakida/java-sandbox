package com.example.completable;

import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.annotations.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableSourceImpl implements CompletableSource {
    @Override
    public void subscribe(@NonNull CompletableObserver observer) {
        log.info("subscribe");
        observer.onComplete();
    }
}
