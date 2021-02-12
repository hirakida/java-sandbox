package com.example;

import java.util.Arrays;

public final class StackTraceMain {

    public static void main(String[] args) {
        printStackTrace();
    }

    private static void printStackTrace() {
        Thread.dumpStack();

        new Throwable().printStackTrace();

        Arrays.stream(Thread.currentThread().getStackTrace())
              .forEach(System.out::println);

    }
}
