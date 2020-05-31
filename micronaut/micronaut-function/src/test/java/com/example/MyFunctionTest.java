package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.micronaut.test.annotation.MicronautTest;

@MicronautTest
public class MyFunctionTest {

    @Inject
    MyFunctionClient client;

    @Test
    public void testFunction() throws Exception {
        FunctionInput body = new FunctionInput();
        body.setName("my-function");
        assertEquals("my-function", client.apply(body).blockingGet().getName());
    }
}
