package com.example;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main {

    public static void main(String[] arg) {
        port(8080);

        get("/hello", (request, response) -> "Hello World!");
        get("/users/:name", (request, response) -> "Selected user: " + request.params(":name"));
    }
}
