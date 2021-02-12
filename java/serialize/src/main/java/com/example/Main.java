package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public final class Main {

    public static void main(String[] args) throws Exception {
        String file1 = "output1.txt";
        String file2 = "output2.txt";

        MyPeriod1 period1 = new MyPeriod1(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now());
        MyPeriod2 period2 = new MyPeriod2(Instant.now().minus(1, ChronoUnit.DAYS), Instant.now());

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file1))) {
            outputStream.writeObject(period1);
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file2))) {
            outputStream.writeObject(period2);
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file1))) {
            MyPeriod1 result = (MyPeriod1) inputStream.readObject();
            System.out.println("period1: " + result);
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file2))) {
            MyPeriod2 result = (MyPeriod2) inputStream.readObject();
            System.out.println("period2: " + result);
        }
    }
}
