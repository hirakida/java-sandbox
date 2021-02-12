package com.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;

public class MyPeriod2 implements Serializable {
    private static final long serialVersionUID = -4835517345451517319L;
    private final Instant start;
    private final Instant end;

    public MyPeriod2(Instant start, Instant end) {
        this.start = start;
        this.end = end;
    }

    public Instant getStart() {
        return start;
    }

    public Instant getEnd() {
        return end;
    }

    @Override
    public String toString() {
        return "start=" + start + ", end=" + end;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        System.out.println("writeObject: " + this);
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        System.out.println("readObject: " + this);
    }
}
