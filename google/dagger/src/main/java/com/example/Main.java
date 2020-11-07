package com.example;

import com.google.common.util.concurrent.ListenableFuture;

public class Main {

    public static void main(String[] args) throws Exception {
        MessageProducerComponent component = DaggerMessageProducerComponent.create();
        ListenableFuture<String> message = component.hello();

        System.out.println("message: " + message.get());
    }
}
