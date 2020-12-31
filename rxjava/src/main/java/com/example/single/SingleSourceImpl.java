package com.example.single;

import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class SingleSourceImpl implements SingleSource<Object> {
    private final Object value;

    @Override
    public void subscribe(SingleObserver<? super Object> observer) {
        log.info("subscribe: {}", value);
        observer.onSuccess(value);
    }
}
