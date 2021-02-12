package com.example;

public class Generics2<T extends CharSequence> {
    private final T t;

    Generics2(T t) {
        this.t = t;
    }

    T get() {
        return t;
    }
}
