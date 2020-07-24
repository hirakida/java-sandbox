package com.example;

public class HelloJNI {
    static {
        System.loadLibrary("hello");
    }

    public native void hello();
}
