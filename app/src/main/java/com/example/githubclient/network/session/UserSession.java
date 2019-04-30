package com.example.githubclient.network.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.githubclient.model.User;

import okhttp3.Credentials;

import static com.example.githubclient.constants.Constants.*;

public class UserSession {

    private SharedPreferences preferences;

    public UserSession(Context context) {
        this.preferences = context.getSharedPreferences("authUser",Context.MODE_PRIVATE);
    }

    public void saveCredentials(User user, String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME,user.getName());
        editor.putString(LOGIN,user.getLogin());
        ///editor.putInt(ID,user.getId());
        //editor.putString(EMAIL,user.getEmail());
        //editor.putInt(FOLLOWERS,user.getFollowers());
        //editor.putInt(FOLLOWING,user.getFollowing());
        editor.putString(AVATAR_URL,user.getAvatarUrl());
        //editor.putString(COMPANY,user.getCompany());
        editor.putString(TOKEN,token);
        editor.apply();
    }

    public String createToken(String username,String password){
        return Credentials.basic(username,password);
    }

    public void invalidate(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(USERNAME);
        editor.remove(LOGIN);
        //editor.remove(ID);
        //editor.remove(EMAIL);
        //editor.remove(FOLLOWERS);
        //editor.remove(FOLLOWING);
        editor.remove(AVATAR_URL);
       // editor.remove(COMPANY);
        editor.remove(TOKEN);
        editor.apply();
    }

    public boolean isExist(){
        return preferences.contains(LOGIN);
    }

}

