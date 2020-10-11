package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        int result = calculator.add(1, 2);
        log.info("result: {}", result);
    }
}
