package com.example.githubclient.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.network.NetworkService;
import com.example.githubclient.network.model.User;
import com.example.githubclient.session.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mUsername;
    private TextInputEditText mPassword;
    private SharedPreferences sharedPreferences;
    private UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("authUser", MODE_PRIVATE);

        mUsername = (TextInputEditText) findViewById(R.id.user_name_et);
        mPassword = (TextInputEditText) findViewById(R.id.password_et);
        userSession = new UserSession(getApplicationContext());
    }


    public void onLoginClick(View view) {

        final String token = userSession.createToken(mUsername.getText().toString(), mPassword.getText().toString());

        NetworkService.getInstance().
                getJSONApi().
                login(token).
                enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        userSession.saveCredentials(user.getLogin(),token);

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

        startActivity(new Intent(this,MainActivity.class));
    }
}
