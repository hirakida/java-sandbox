package com.example;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MessageModule());
        MessageService service = injector.getInstance(MessageService.class);
        service.hello();
    }
}