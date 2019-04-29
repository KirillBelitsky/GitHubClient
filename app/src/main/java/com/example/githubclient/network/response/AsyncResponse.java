package com.example.githubclient.network.response;

import com.example.githubclient.network.model.User;

public interface AsyncResponse {
    void processFinish(String result);
}
