package com.example;

import java.io.Serializable;
import java.time.Instant;

public class MyPeriod1 implements Serializable {
    private static final long serialVersionUID = -6685940415365371119L;
    private final Instant start;
    private final Instant end;
    private final transient Instant now;

    public MyPeriod1(Instant start, Instant end) {
        this.start = start;
        this.end = end;
        now = Instant.now();
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    public Instant getNow() {
        return now;
    }

    @Override
    public String toString() {
        return "start=" + start + ", end=" + end + ", now=" + now;
    }
}
