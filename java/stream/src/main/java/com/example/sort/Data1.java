package com.example.sort;

public class Data1 {
    private final Integer code;

    public Data1(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public int diff(Data1 arg) {
        return code - arg.getCode();
    }
}
