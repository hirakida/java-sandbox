package com.example;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;

/**
 * void accept(T t)
 */
public class ConsumerMain {

    public static void main(String[] args) {

        Consumer1 consumer1 = new Consumer1();
        consumer1.accept("test1");

        Consumer<String> consumer2 = System.out::println;
        consumer2.accept("test2");

        BiConsumer1 consumer3 = new BiConsumer1();
        consumer3.accept("test3", 3);
    }

    public static class Consumer1 implements Consumer<String> {
        @Override
        public void accept(String value) {
            System.out.println(value);
        }
    }

    public static class IntConsumer1 implements IntConsumer {
        @Override
        public void accept(int value) {
            System.out.println(value);
        }
    }

    public static class LongConsumer1 implements LongConsumer {
        @Override
        public void accept(long value) {
            System.out.println(value);
        }
    }

    public static class DoubleConsumer1 implements DoubleConsumer {
        @Override
        public void accept(double value) {
            System.out.println(value);
        }
    }

    public static class BiConsumer1 implements BiConsumer<String, Integer> {
        @Override
        public void accept(String t, Integer value) {
            System.out.println(t + ':' + value);
        }
    }

    public static class ObjIntConsumer1 implements ObjIntConsumer<String> {
        @Override
        public void accept(String t, int value) {
            System.out.println(t + ':' + value);
        }
    }

    public static class ObjLongConsumer1 implements ObjLongConsumer<String> {
        @Override
        public void accept(String t, long value) {
            System.out.println(t + ':' + value);
        }
    }

    public static class ObjDoubleConsumer1 implements ObjDoubleConsumer<String> {
        @Override
        public void accept(String t, double value) {
            System.out.println(t + ':' + value);
        }
    }
}
