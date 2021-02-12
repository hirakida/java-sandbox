package com.example.atomic;

import java.util.concurrent.atomic.LongAdder;

public final class LongAdderMain {

    public static void main(String[] args) {
        LongAdder longAddr = new LongAdder();
        longAddr.add(10);
        System.out.println(longAddr);  // 10
        longAddr.add(20);
        System.out.println(longAddr);  // 30

        longAddr.increment();
        System.out.println(longAddr);  // 31
        longAddr.decrement();
        System.out.println(longAddr);  // 30

        long l = longAddr.longValue();
        int i = longAddr.intValue();
        System.out.println(l + "," + i);

        longAddr.reset();
        System.out.println(longAddr);   // 0
    }
}
