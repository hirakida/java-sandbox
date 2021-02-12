package com.example.atomic;

import java.util.concurrent.atomic.LongAccumulator;

public class LongAccumulatorMain {

    public static void main(String[] args) {
        LongAccumulator accumulator1 = new LongAccumulator((x, y) -> x + y * 2, 0L);

        accumulator1.accumulate(1);
        System.out.println(accumulator1);   // 2 (0 + 1 * 2)
        accumulator1.accumulate(2);
        System.out.println(accumulator1);   // 6 (2 + 2 * 2)
        accumulator1.accumulate(3);
        System.out.println(accumulator1);   // 12 (6 + 3 * 2)
        accumulator1.accumulate(4);
        System.out.println(accumulator1);   // 20 (12 + 4 * 2)

        // same as LongAdder
        LongAccumulator accumulator2 = new LongAccumulator((x, y) -> x + y, 0L);
        accumulator2.accumulate(1);
        System.out.println(accumulator2);   // 1 (0 + 1)
        accumulator2.accumulate(2);
        System.out.println(accumulator2);   // 3 (1 + 2)
    }
}
