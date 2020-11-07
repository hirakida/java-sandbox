package com.example;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import dagger.producers.Production;

@Module
public class ExecutorModule {
    @Provides
    @Production
    static Executor executor() {
        return Executors.newCachedThreadPool();
    }
}
