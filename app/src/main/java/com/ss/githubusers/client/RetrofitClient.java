package com.ss.githubusers.client;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://api.github.com";
    private static final String OAUTH_TOKEN = "056486993dc5c9ed18019405c3ebb6dbb4c07860";


    private static RetrofitClient mInstance;

    private Retrofit mRetrofit;

    private RetrofitClient() {
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .addHeader("Authorization", "token " + OAUTH_TOKEN)
                                .build();

                        return chain.proceed(request);
                    }
                });

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpBuilder.build())
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