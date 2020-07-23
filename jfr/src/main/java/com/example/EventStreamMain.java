package com.example;

import java.io.IOException;
import java.nio.file.Path;

import jdk.jfr.consumer.EventStream;

public class EventStreamMain {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            return;
        }

        try (EventStream es = EventStream.openRepository(Path.of(args[0]))) {
            es.onEvent("hirakida.Hello", System.out::println);
            es.onEvent("jdk.CPULoad", System.out::println);
            es.start();
        }
    }
}
