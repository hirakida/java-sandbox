package com.example;

import static java.util.stream.Collectors.toMap;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public enum Operation {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private static final Map<String, Operation> VALUES_MAP = Stream.of(values())
                                                                   .collect(toMap(Object::toString, e -> e));
    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public static Optional<Operation> from(String symbol) {
        return Optional.ofNullable(VALUES_MAP.get(symbol));
    }

    @Override
    public String toString() {
        return symbol;
    }

    public abstract double apply(double x, double y);
}
