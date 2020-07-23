package com.example;

import java.time.Instant;

public final class Main {

    public static void main(String[] args) {
        CLibrary.INSTANCE.printf("now=%d\n", Instant.now().toEpochMilli());
        System.out.println(CLibrary.INSTANCE.sin(90));

        HelloLibrary.INSTANCE.hello();
        HelloLibrary.INSTANCE.goodbye();
    }
}
