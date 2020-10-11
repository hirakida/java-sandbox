package com.example;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class Main {
    private static final String TOPIC = "topic1";
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(Main::publish);
        executorService.execute(Main::subscribe);
        executorService.shutdown();
    }

    private static void publish() {
        try (ZContext context = new ZContext()) {
            Socket publisher = context.createSocket(SocketType.PUB);
            publisher.bind("tcp://*:5556");

            while (!Thread.currentThread().isInterrupted()) {
                publisher.sendMore(TOPIC);
                String data = LocalDateTime.now().toString();
                publisher.send(data);

                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    log.error("{}", e.getMessage(), e);
                }
            }
        }
    }

    private static void subscribe() {
        try (ZContext context = new ZContext()) {
            Socket subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect("tcp://localhost:5556");
            subscriber.subscribe(TOPIC.getBytes(ZMQ.CHARSET));

            while (!Thread.currentThread().isInterrupted()) {
                String topic = subscriber.recvStr();
                if (subscriber.hasReceiveMore()) {
                    String data = subscriber.recvStr();
                    log.info("{} {}", topic, data);
                }
            }
        }
    }
}
