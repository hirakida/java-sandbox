package com.example;

import java.io.IOException;
import java.util.function.Function;

import io.micronaut.function.FunctionBean;
import io.micronaut.function.executor.FunctionInitializer;

@FunctionBean("my-function")
public class MyFunction extends FunctionInitializer implements Function<FunctionInput, FunctionOutput> {

    @Override
    public FunctionOutput apply(FunctionInput input) {
        return new FunctionOutput(input.getName());
    }

    public static void main(String... args) throws IOException {
        MyFunction function = new MyFunction();
        function.run(args, context -> function.apply(context.get(FunctionInput.class)));
    }
}
