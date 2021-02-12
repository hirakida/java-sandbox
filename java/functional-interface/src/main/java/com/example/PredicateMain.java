package com.example;

import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

/**
 * boolean test(T t)
 */
public class PredicateMain {

    public static void main(String[] args) {

        Predicate1 predicate1 = new Predicate1();
        System.out.println("predicate1: " + predicate1.test("word1"));

        Predicate<String> predicate2 = "word2"::equals;
        System.out.println("predicate2: " + predicate2.test("word1"));

        System.out.println("predicate1 and 2: " + predicate1.and(predicate2).test("word1"));
        System.out.println("predicate1 or 2: " + predicate1.or(predicate2).test("word1"));

        BiPredicate1 biPredicate = new BiPredicate1();
        System.out.println("BiPredicate: " + biPredicate.test("word1", 1));
    }

    public static class Predicate1 implements Predicate<String> {
        @Override
        public boolean test(String value) {
            return "word1".equals(value);
        }
    }

    public static class IntPredicate1 implements IntPredicate {
        @Override
        public boolean test(int value) {
            return value == 1;
        }
    }

    public static class LongPredicate1 implements LongPredicate {
        @Override
        public boolean test(long value) {
            return value == 1L;
        }
    }

    public static class DoublePredicate1 implements DoublePredicate {
        @Override
        public boolean test(double value) {
            return (int) value == 1;
        }
    }

    public static class BiPredicate1 implements BiPredicate<String, Integer> {
        @Override
        public boolean test(String t, Integer value) {
            return "word1".equals(t) && value == 1;
        }
    }
}
