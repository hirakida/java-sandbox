package com.example;

import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;

import jdk.jfr.Configuration;
import jdk.jfr.consumer.RecordingStream;

public class RecordingStreamMain {

    public static void main(String[] args) throws IOException, ParseException {
        Configuration config = Configuration.getConfiguration("default");
        System.out.println("description: " + config.getDescription());
        System.out.println("settings:");
        config.getSettings().forEach((key, value) -> System.out.println(key + ": " + value));

        try (RecordingStream rs = new RecordingStream(config)) {
            rs.enable("jdk.CPULoad").withPeriod(Duration.ofSeconds(10));

            rs.onEvent("jdk.CPULoad", System.out::println);
            rs.onEvent("jdk.GarbageCollection", System.out::println);
            rs.onEvent("jdk.SafepointCleanup", System.out::println);
            rs.onEvent("jdk.JVMInformation", System.out::println);

            rs.start();
        }
    }
}
