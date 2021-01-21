package com.example;

import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class Main {
    private static final URI BASE_URI = URI.create("http://localhost:8080/");

    public static void main(String[] args) {
        ResourceConfig config = new ResourceConfig().packages(Main.class.getPackage().getName());
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, config, false);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));

        try {
            server.start();

            Thread.currentThread().join();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
