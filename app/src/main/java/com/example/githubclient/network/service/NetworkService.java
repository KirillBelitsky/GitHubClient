package com.example.githubclient.network.service;

import com.example.githubclient.network.api.FollowerApi;
import com.example.githubclient.network.api.IssueApi;
import com.example.githubclient.network.api.RepoApi;
import com.example.githubclient.network.api.UserApi;

import okhttp3.internal.http.RetryAndFollowUpInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://api.github.com/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public UserApi getUserApi() {
        return mRetrofit.create(UserApi.class);
    }

    public RepoApi getRepoApi(){
        return mRetrofit.create(RepoApi.class);
    }

    public IssueApi getIssueApi(){
        return mRetrofit.create(IssueApi.class);
    }

    public FollowerApi getFollowApi(){
        return mRetrofit.create(FollowerApi.class);
    }
}
