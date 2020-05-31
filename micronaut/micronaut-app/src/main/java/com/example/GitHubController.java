package com.example;

import com.example.client.GitHubApiClient;
import com.example.model.User;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.reactivex.Single;

@Controller("/github")
public class GitHubController {
    private final GitHubApiClient client;

    public GitHubController(GitHubApiClient client) {
        this.client = client;
    }

    @Get("/users/{username}")
    public Single<User> getUser(String username) {
        return client.getUser(username);
    }
}
