package com.example;

import com.google.common.collect.Range;

public class RangeMain {

    public static void main(String[] args) {
        // 100 - 200
        Range<Integer> range1 = Range.closed(100, 200);
        System.out.println("--- closed ---");
        System.out.println(range1.contains(99));     // false
        System.out.println(range1.contains(100));    // true
        System.out.println(range1.contains(101));    // true
        System.out.println(range1.contains(200));    // true
        System.out.println(range1.contains(201));    // false

        // 101 - 199
        Range<Integer> range2 = Range.open(100, 200);
        System.out.println("--- open ---");
        System.out.println(range2.contains(99));     // false
        System.out.println(range2.contains(100));    // false
        System.out.println(range2.contains(101));    // true
        System.out.println(range2.contains(200));    // false
        System.out.println(range2.contains(201));    // false

        // 101 - 200
        Range<Integer> range3 = Range.openClosed(100, 200);
        System.out.println("--- openClosed ---");
        System.out.println(range3.contains(99));     // false
        System.out.println(range3.contains(100));    // false
        System.out.println(range3.contains(101));    // true
        System.out.println(range3.contains(200));    // true
        System.out.println(range3.contains(201));    // false

        // 100 - 199
        Range<Integer> range4 = Range.closedOpen(100, 200);
        System.out.println("--- closedOpen ---");
        System.out.println(range4.contains(99));     // false
        System.out.println(range4.contains(100));    // true
        System.out.println(range4.contains(101));    // true
        System.out.println(range4.contains(200));    // false
        System.out.println(range4.contains(201));    // false
    }
}
