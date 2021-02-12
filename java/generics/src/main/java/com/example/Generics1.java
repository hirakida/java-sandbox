package com.example;

public class Generics1<T> {
    private final T t;

    Generics1(T t) {
        this.t = t;
    }

    T get1() {
        return t;
    }

    <E> E get2(E e) {
        return e;
    }
}
