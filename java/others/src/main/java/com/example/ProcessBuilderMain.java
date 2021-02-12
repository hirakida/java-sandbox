package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ProcessBuilderMain {

    public static void main(String[] args) {
        run("java", "-version");
        run("sh", "-c", "echo 'Hello!'");
    }

    private static int run(String... command) {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);

        try {
            Process process = pb.start();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                br.lines().forEach(System.out::println);
                return process.waitFor();
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
}
