package com.example;

import javax.inject.Inject;

public class MessageService {
    private final Message message;

    @Inject
    public MessageService(Message message) {
        this.message = message;
    }

    public void hello() {
        message.hello();
    }
}
