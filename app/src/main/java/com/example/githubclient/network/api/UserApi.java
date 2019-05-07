package com.example.githubclient.network.api;

import com.example.githubclient.model.User;
import com.example.githubclient.model.UserDataEdit;
import com.example.githubclient.network.response.SearchUsersResponce;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("user")
    Call<User> login(@Header("Authorization") String token);

    @GET("/users/{name}")
    Call<User> getUserByLogin(@Path("name") String name);

    @PATCH("user")
    Call<User> updateUser(@Header("Authorization") String token,@Body UserDataEdit user);

    @GET("user")
    Call<User> getCurrentUser(@Header("Authorization") String token);

    @GET("/search/users")
    Call<SearchUsersResponce> searchUsers(@Query("q") String query, @Query("page") String page, @Query("per_page") String per_page);
}
