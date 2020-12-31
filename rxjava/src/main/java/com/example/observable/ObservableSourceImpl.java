package com.example.observable;

import java.util.Random;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObservableSourceImpl implements ObservableSource<Integer> {
    @Override
    public void subscribe(@NonNull Observer<? super Integer> observer) {
        int i = new Random().nextInt();
        log.info("subscribe: {}", i);
        observer.onNext(i);
        observer.onComplete();
    }
}
