package com.ss.githubusers.client;

import com.ss.githubusers.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {

    String OAUTH_TOKEN = "bf3c59a02546a932ffc48e61ad0cbae074b64b84";

    @Headers({"Authorization: token " + OAUTH_TOKEN,
            "rel: next"})
    @GET("/users")
    Call<List<User>> getUsers(@Query("per_page") int per_page);

    @Headers({"Authorization: token " + OAUTH_TOKEN})
    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
}
