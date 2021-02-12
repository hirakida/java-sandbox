package com.example;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class Main {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Runnable
        CompletableFuture.runAsync(() -> log.info("runAsync"));
        CompletableFuture.runAsync(() -> log.info("runAsync"), executor);

        // Supplier, Consumer
        CompletableFuture.supplyAsync(() -> 1)
                         .thenAccept(value -> log.info("accept: {}", value));
        CompletableFuture.supplyAsync(() -> 2, executor)
                         .thenAccept(value -> log.info("accept: {}", value));
        CompletableFuture.supplyAsync(() -> 3, executor)
                         .thenAcceptAsync(value -> log.info("accept: {}", value), executor);

        // Supplier, BiConsumer
        CompletableFuture.supplyAsync(() -> 4, executor)
                         .whenComplete((value, t) -> log.info("complete: {} {}", value, t));
        CompletableFuture.supplyAsync(() -> 5, executor)
                         .whenCompleteAsync((value, t) -> log.info("complete: {} {}", value, t), executor);

        // Supplier, Function, Consumer
        CompletableFuture.supplyAsync(() -> 6, executor)
                         .thenApply(value -> value * 2)
                         .thenAccept(value -> log.info("accept: {}", value));
        CompletableFuture.supplyAsync(() -> 7, executor)
                         .thenApplyAsync(value -> value * 2, executor)
                         .thenAcceptAsync(value -> log.info("accept: {}", value), executor);

        // Supplier, (CompletionStage, Consumer)
        CompletableFuture.supplyAsync(() -> {
                                          sleep(1);
                                          return 1;
                                      },
                                      executor)
                         .acceptEitherAsync(CompletableFuture.supplyAsync(() -> 2, executor),
                                            value -> log.info("acceptEither: {}", value), executor);

        // Supplier, Function<T, CompletionStage>, Function, BiConsumer
        CompletableFuture.supplyAsync(() -> 1, executor)
                         .thenComposeAsync(value -> CompletableFuture.supplyAsync(() -> value * 2, executor),
                                           executor)
                         .thenApplyAsync(value -> value * 2, executor)
                         .whenCompleteAsync((value, t) -> log.info("complete: {}", value), executor);

        sleep(1);
        executor.shutdown();
    }

    private static void sleep(long timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
