package com.example.githubclient.session;

import android.content.Context;
import android.content.SharedPreferences;

import okhttp3.Credentials;

import static com.example.githubclient.constants.Constants.*;

public class UserSession {

    private SharedPreferences preferences;

    public UserSession(Context context) {
        this.preferences = context.getSharedPreferences("UserPreference",Context.MODE_PRIVATE);
    }

    public void saveCredentials(String username, String token){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(USERNAME,username);
        editor.putString(TOKEN,token);
        editor.apply();
    }

    public String createToken(String username,String password){
        return Credentials.basic(username,password);
    }

    private void invalidate(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(TOKEN);
        editor.remove(USERNAME);
        editor.apply();
    }

    public boolean isExist(){
        return preferences.contains(USERNAME);
    }
}
