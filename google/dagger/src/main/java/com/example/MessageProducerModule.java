package com.example;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import dagger.producers.ProducerModule;
import dagger.producers.Produces;

@ProducerModule
public class MessageProducerModule {
    @Produces
    String hello(Message message) {
        return message.getMessage();
    }

    @Produces
    ListenableFuture<Message> message() {
        return Futures.immediateFuture(new Message("Hello!"));
    }
}
