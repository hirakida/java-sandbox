package com.example;

import com.sun.jna.Library;
import com.sun.jna.Native;

interface HelloLibrary extends Library {
    HelloLibrary INSTANCE = Native.load("hello", HelloLibrary.class);

    void hello();

    void goodbye();
}
