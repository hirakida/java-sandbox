package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;

public final class ServerSocketMain {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> run(socket)).start();
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static void run(Socket socket) {
        try (InputStream is = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            for (String line; (line = reader.readLine()) != null && !line.isEmpty(); ) {
                System.out.println(line);
            }

            try (OutputStream os = socket.getOutputStream();
                 PrintWriter writer = new PrintWriter(os)) {
                writer.println("HTTP/1.0 200 OK");
                writer.println("Content-Type: text/html");
                writer.println();
                writer.println("<h1>Hello!</h1>");
                writer.println(LocalDateTime.now());
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
