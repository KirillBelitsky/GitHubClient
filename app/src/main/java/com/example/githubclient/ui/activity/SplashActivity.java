package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.githubclient.network.session.UserSession;

import io.github.kbiakov.codeview.classifier.CodeProcessor;


public class SplashActivity extends AppCompatActivity {

    private UserSession userSession;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);

        userSession = new UserSession(this.getApplicationContext());
        CodeProcessor.init(this);

        process();
    }

    private void process(){
        if(userSession.isExist()){
            startActivity(new Intent(this, MainActivity.class));
        }
        else
            startActivity(new Intent(this, LoginActivity.class));

        finish();
    }
}
