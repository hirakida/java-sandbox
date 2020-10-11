package com.example;

import com.google.inject.AbstractModule;

public class MessageModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Message.class).to(MessageImpl.class);
    }
}
