package com.example.githubclient.network.api;

import com.example.githubclient.model.User;
import com.example.githubclient.model.UserDataEdit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface UserApi {

    @GET("user")
    Call<User> login(@Header("Authorization") String token);

    @GET("/users/{name}")
    Call<User> getUserByLogin(@Path("name") String name);

    @PATCH("user")
    Call<User> updateUser(@Header("Authorization") String token,@Body UserDataEdit user);

    @GET("user")
    Call<User> getCurrentUser(@Header("Authorization") String token);
}
