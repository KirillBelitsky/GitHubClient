package com.example.githubclient.network.api;

import com.example.githubclient.network.model.Repository;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

import java.util.List;

public interface RepoApi {

    @GET("/users/{name}/starred")
    Call<Repository> getStarredRepoByLogin(String login);

    @GET("/users/{name}/repos")
    Call<List<Repository>> getRepoByLogin(String login);

}

