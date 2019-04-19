package com.example.githubclient.network.api;

import com.example.githubclient.network.model.Post;
import com.example.githubclient.network.model.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface UserApi {

    @GET("user")
    Call<User> login(@Header("Authorization") String token);

    @GET("/users/{name}")
    Call<Post> getPostWithID(@Path("name") String name);
}
