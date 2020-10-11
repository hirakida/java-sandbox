package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.github.netmikey.logunit.api.LogCapturer;

public class CalculatorTest {
    @RegisterExtension
    final LogCapturer logCapturer = LogCapturer.create().captureForType(Calculator.class);
    private final Calculator calculator = new Calculator();

    @Test
    public void add() {
        assertEquals(3, calculator.add(1, 2));
        logCapturer.assertContains("1 + 2");
    }
}
