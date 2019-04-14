package com.example.githubclient.network.api;

import com.example.githubclient.network.model.Post;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/users/{name}")
    public Call<Post> getPostWithID(@Path("name") String name);
}
