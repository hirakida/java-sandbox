package com.example.client;

import com.example.model.User;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Single;

@Client("https://api.github.com")
@Header(name = "User-Agent", value = "Micronaut-Http-Client")
public interface GitHubApiClient {

    @Get("/users/{username}")
    Single<User> getUser(String username);
}
