package com.example.completable;

import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CompletableOnSubscribeImpl implements CompletableOnSubscribe {
    private final String value;

    @Override
    public void subscribe(CompletableEmitter emitter) throws Exception {
        log.info("subscribe: {}", value);
        emitter.onComplete();
    }
}
