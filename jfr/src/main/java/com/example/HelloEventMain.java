package com.example;

import java.util.concurrent.TimeUnit;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;
import jdk.jfr.Name;

public class HelloEventMain {

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            HelloEvent event = new HelloEvent();
            event.message = "Hello!";
            event.begin();
            TimeUnit.SECONDS.sleep(5);
            event.commit();
        }
    }

    @Name("hirakida.Hello")
    @Label("Hello")
    @Category("Hello")
    @Description("Hello Event")
    public static class HelloEvent extends Event {
        @Label("Message")
        String message;
    }
}
