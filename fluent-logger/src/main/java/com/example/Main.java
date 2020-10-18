package com.example;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.fluentd.logger.FluentLogger;

public class Main {
    private static final FluentLogger FLUENT_LOGGER = FluentLogger.getLogger("app");
    private static final AtomicInteger COUNT = new AtomicInteger();

    public static void main(String[] args) {
        while (true) {
            FLUENT_LOGGER.log("tag1", Map.of("datetime", LocalDateTime.now(),
                                             "clock", Clock.systemDefaultZone().millis()));

            FLUENT_LOGGER.log("tag2", Map.of("count", COUNT.incrementAndGet()));

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
                break;
            }
        }

        FLUENT_LOGGER.close();
    }
}
