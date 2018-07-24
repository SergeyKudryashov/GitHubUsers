package com.ss.githubusers.client;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.github.com";

    private static RetrofitClient mInstance;

    private Retrofit mRetrofit;

    private RetrofitClient() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public GitHubService getService() {
        return mRetrofit.create(GitHubService.class);
    }
}