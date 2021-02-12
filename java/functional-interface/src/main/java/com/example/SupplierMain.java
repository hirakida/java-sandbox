package com.example;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * T get()
 */
public class SupplierMain {

    public static void main(String[] args) {

        Supplier1 supplier1 = new Supplier1();
        System.out.println("Supplier1: " + supplier1.get());

        Supplier<String> supplier2 = () -> "Supplier2";
        System.out.println("Supplier<String>: " + supplier2.get());

        Supplier<Long> supplier3 = () -> 3L;
        System.out.println("Supplier<Long>: " + supplier3.get());

        LongSupplier supplier4 = () -> 3L;
        System.out.println("LongSupplier: " + supplier4.getAsLong());
    }

    public static class Supplier1 implements Supplier<String> {
        @Override
        public String get() {
            return "Supplier1";
        }
    }

    public static class IntSupplierImpl implements IntSupplier {
        @Override
        public int getAsInt() {
            return 1;
        }
    }

    public static class LongSupplierImpl implements LongSupplier {
        @Override
        public long getAsLong() {
            return 1L;
        }
    }

    public static class DoubleSupplierImpl implements DoubleSupplier {
        @Override
        public double getAsDouble() {
            return 1.0;
        }
    }

    public static class BooleanSupplierImpl implements BooleanSupplier {
        @Override
        public boolean getAsBoolean() {
            return true;
        }
    }
}
