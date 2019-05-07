package com.example.githubclient.network.response;

import com.example.githubclient.model.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchUsersResponce {

    @SerializedName("items")
    @Expose
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
