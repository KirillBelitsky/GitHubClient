package com.example.githubclient.network.api;

import com.example.githubclient.model.Repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface RepoApi {

    @GET("/users/{name}/starred")
    Call<List<Repository>> getStarredRepoByLogin(@Path("name") String login);

    @GET("/users/{name}/repos")
    Call<List<Repository>> getRepoByLogin(@Path("name") String name);

}

