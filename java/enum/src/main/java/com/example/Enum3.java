package com.example;

import java.util.Optional;
import java.util.stream.Stream;

public enum Enum3 {
    FOO("foo", 1),
    BAR("bar", 2),
    BAZ("baz", 3);

    private final String label;
    private final int code;

    Enum3(String label, int code) {
        this.label = label;
        this.code = code;
        System.out.println("constructor: " + this);
    }

    public Optional<Enum3> of(int code) {
        return Stream.of(values())
                     .filter(x -> x.getCode() == code)
                     .findFirst();
    }

    public String getLabel() {
        return label;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s{label=%s,code=%d}", name(), label, code);
    }
}
