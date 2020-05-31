package com.example;

import io.micronaut.function.client.FunctionClient;
import io.micronaut.http.annotation.Body;
import io.reactivex.Single;
import javax.inject.Named;

@FunctionClient
public interface MyFunctionClient {

    @Named("my-function")
    Single<FunctionOutput> apply(@Body FunctionInput body);
}
