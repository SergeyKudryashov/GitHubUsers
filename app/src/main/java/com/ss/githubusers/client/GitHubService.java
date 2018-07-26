package com.ss.githubusers.client;

import com.ss.githubusers.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GitHubService {


    @Headers({"rel: next"})
    @GET("/users")
    Call<List<User>> getUsers(@Query("per_page") int per_page);

    @GET("/users/{username}")
    Call<User> getUser(@Path("username") String username);
}
