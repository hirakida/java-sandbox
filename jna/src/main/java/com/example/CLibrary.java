package com.example;

import com.sun.jna.Library;
import com.sun.jna.Native;

interface CLibrary extends Library {
    CLibrary INSTANCE = Native.load("c", CLibrary.class);

    void printf(String format, Object... args);

    double sin(double x);
}
