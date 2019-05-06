package com.example.githubclient.network.api;

import com.example.githubclient.model.Issue;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IssueApi {

    @GET("/issues")
    Call<List<Issue>> getIssues(@Header("Authorization") String token, @Query("filter") String filter,
                         @Query("state") String state);
}
