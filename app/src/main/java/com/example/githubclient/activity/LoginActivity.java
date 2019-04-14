package com.example.githubclient.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.githubclient.R;

public class LoginActivity extends AppCompatActivity {

    private TextView mUsername;
    private TextView mPassword;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("authUser",MODE_PRIVATE);

        mUsername = (TextView) findViewById(R.id.user_name_et);
        mPassword = (TextView) findViewById(R.id.password_et);
    }


    public void onLoginClick(View view){
        System.out.println(mUsername.toString() + mPassword.toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(mUsername.toString(),mPassword.toString());
    }
}
