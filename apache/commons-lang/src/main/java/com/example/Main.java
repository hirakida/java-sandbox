package com.example;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.time.StopWatch;

public final class Main {

    public static void main(String[] args) {
        booleanUtils();
        stopWatch();
    }

    private static void booleanUtils() {
        System.out.println(BooleanUtils.toBoolean(0));  // false
        System.out.println(BooleanUtils.toBoolean(1));  // true
        System.out.println(BooleanUtils.toBoolean(2));  // true
        System.out.println(BooleanUtils.toBoolean(-1)); // true

        System.out.println(BooleanUtils.toStringTrueFalse(true));   // "true"
        System.out.println(BooleanUtils.toStringTrueFalse(false));  // "false"
    }

    private static void stopWatch() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        stopWatch.stop();
        System.out.println(stopWatch);
    }
}
