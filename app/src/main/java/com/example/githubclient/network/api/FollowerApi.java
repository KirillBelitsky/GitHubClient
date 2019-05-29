package com.example.githubclient.network.api;

import com.example.githubclient.model.Follower;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FollowerApi {

    @GET("/users/{login}/followers")
    Call<List<Follower>> getUserFollowers(@Path("login") String login);

    @GET("/user/followers")
    Call<List<Follower>> getOwnFollowers(@Header("Authorization") String token);

    @GET("/users/{login}/following")
    Call<List<Follower>> getUserFollowing(@Path("login") String login);

    @GET("/user/following")
    Call<List<Follower>> getOwnFollowing(@Header("Authorization") String token);
 }
