package com.example;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class TimeMain {

    public static void main(String[] args) {
        ZoneId zoneId = ZoneId.of("Asia/Tokyo");
        System.out.println(LocalDateTime.now().atZone(zoneId).toEpochSecond());
        System.out.println(System.currentTimeMillis() / 1000);
    }
}
