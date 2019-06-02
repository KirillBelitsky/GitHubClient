package com.example.githubclient.network.api;

import com.example.githubclient.model.Repository;
import com.example.githubclient.model.RepositoryFile;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

import java.util.List;

public interface RepoApi {

    @GET("/users/{name}/starred")
    Call<List<Repository>> getStarredRepoByLogin(@Path("name") String login);

    @GET("/users/{name}/repos")
    Call<List<Repository>> getRepositoriesByLogin(@Path("name") String name);

    @GET("/user/repos")
    Call<List<Repository>> getOwnRepositories(@Header("Authorization") String token);

    @GET("/repos/{login}/{repo}/contents/{path}")
    Call<List<RepositoryFile>> getRepoFiles(@Path("login") String login, @Path("repo") String repo,
                                            @Path("path") String path);

    @GET("{fileDownloadPath}")
    Call<String> getFileContent(@Path("fileDownloadPath") String path);
}

