package com.example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/{username}")
    Call<User> getUser(@Path("username") String username);
}
