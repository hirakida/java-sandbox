package com.example;

import java.util.stream.Stream;

public final class EnumStreamMain {

    public static void main(String[] args) {
        Stream.of(ENUM.values())
              .forEach(System.out::print);
    }

    private enum ENUM {
        AAA,
        BBB,
        CCC
    }
}
