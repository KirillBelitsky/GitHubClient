package com.example.githubclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.githubclient.session.UserSession;

public class StartActivity extends AppCompatActivity {

    private UserSession userSession;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        userSession = new UserSession(this.getApplicationContext());

        process();
    }

    private void process(){
        if(true){
            startActivity(new Intent(this, MainActivity.class));
        }
        else
            startActivity(new Intent(this, LoginActivity.class));

        finish();
    }
}
