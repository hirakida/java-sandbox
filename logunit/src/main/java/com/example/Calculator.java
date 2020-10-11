package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Calculator {
    private static final Logger log = LoggerFactory.getLogger(Calculator.class);

    public int add(int num1, int num2) {
        log.info("{} + {}", num1, num2);
        return num1 + num2;
    }
}
