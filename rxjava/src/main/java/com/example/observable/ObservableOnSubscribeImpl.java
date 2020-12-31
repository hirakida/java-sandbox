package com.example.observable;

import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObservableOnSubscribeImpl implements ObservableOnSubscribe<Object> {
    private final List<Object> objects;

    @Override
    public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
        for (Object object : objects) {
            if (emitter.isDisposed()) {
                return;
            }
            emitter.onNext(object);
        }
        emitter.onComplete();
    }
}
