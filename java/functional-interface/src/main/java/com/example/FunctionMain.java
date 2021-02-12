package com.example;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

/**
 * R apply(T t)
 */
public class FunctionMain {

    public static void main(String[] args) {

        Function1 func1 = new Function1();
        System.out.println("Function1: " + func1.apply(1));

        Function<Integer, String> func2 = t -> "apply: " + t;
        System.out.println("Function<Integer, String>: " + func2.apply(2));

        BiFunction<Integer, Long, String> biFunc1 = (t, u) -> "apply: " + t + ',' + u;
        System.out.println("BiFunction: " + biFunc1.apply(1, 2L));

        unaryOperator();
        binaryOperator();
    }

    private static void unaryOperator() {
        // T apply(T t)

        UnaryOperator<Long> operator1 = t -> t * 2;
        System.out.println(operator1.apply(1L));

        // IntUnaryOperator
        IntUnaryOperator operator2 = t -> t * 2;
        System.out.println(operator2.applyAsInt(2));

        // LongUnaryOperator
        LongUnaryOperator operator3 = t -> t * 2;
        System.out.println(operator3.applyAsLong(3));

        // DoubleUnaryOperator
        DoubleUnaryOperator operator4 = t -> t * 2;
        System.out.println(operator4.applyAsDouble(4.0));
    }

    private static void binaryOperator() {
        // T apply(T t, T t)

        BinaryOperator<Long> operator1 = (l1, l2) -> l1 + l2;
        System.out.println(operator1.apply(1L, 2L));

        // IntBinaryOperator
        IntBinaryOperator operator2 = Integer::sum;
        System.out.println(operator2.applyAsInt(1, 2));

        // LongBinaryOperator
        LongBinaryOperator operator3 = Long::sum;
        System.out.println(operator3.applyAsLong(1L, 2L));

        // DoubleBinaryOperator
        DoubleBinaryOperator operator4 = Double::sum;
        System.out.println(operator4.applyAsDouble(1.1, 2.2));
    }

    public static class Function1 implements Function<Integer, String> {
        @Override
        public String apply(Integer t) {
            return "apply: " + t;
        }
    }

    public static class IntFunction1 implements IntFunction<String> {
        @Override
        public String apply(int t) {
            return "apply: " + t;
        }
    }

    public static class IntToLongFunction1 implements IntToLongFunction {
        @Override
        public long applyAsLong(int t) {
            return t;
        }
    }

    public static class ToIntFunction1 implements ToIntFunction<String> {
        @Override
        public int applyAsInt(String t) {
            return Integer.parseInt(t);
        }
    }

    public static class BiFunction1 implements BiFunction<Integer, Long, String> {
        @Override
        public String apply(Integer t, Long u) {
            return "apply: " + t + ',' + u;
        }
    }
}
