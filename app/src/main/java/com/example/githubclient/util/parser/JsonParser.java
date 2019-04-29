package com.example.githubclient.util.parser;

import com.example.githubclient.model.Repository;
import com.example.githubclient.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {

    public static User parseUser(String json){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

      return gson.fromJson(json,User.class);
    }

    public static List<Repository> parseRepositories(String json){
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        Type listType = new TypeToken<ArrayList<Repository>>(){}.getType();
        List<Repository> repositoryList = new Gson().fromJson(json, listType);

        System.out.println("gsdgfsdfgs" + repositoryList.toString());

        return repositoryList;
    }
}
