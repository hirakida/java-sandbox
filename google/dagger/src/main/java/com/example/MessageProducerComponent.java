package com.example;

import com.google.common.util.concurrent.ListenableFuture;

import dagger.producers.ProductionComponent;

@ProductionComponent(modules = { ExecutorModule.class, MessageProducerModule.class })
public interface MessageProducerComponent {
    ListenableFuture<String> hello();
}
