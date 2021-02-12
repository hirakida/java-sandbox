package com.example;

public enum Enum2 {
    FOO("foo"),
    BAR("bar");

    private final String label;

    Enum2(String label) {
        this.label = label;
        System.out.println("constructor: " + this);
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return String.format("%s{label=%s}", name(), label);
    }
}
