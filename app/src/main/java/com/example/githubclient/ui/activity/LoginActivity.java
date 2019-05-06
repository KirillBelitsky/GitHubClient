package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.githubclient.R;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.model.User;
import com.example.githubclient.network.session.UserSession;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{1,38}\\$";
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}\\$";

    private TextInputEditText mUsername;
    private TextInputEditText mPassword;
    private SharedPreferences sharedPreferences;
    private UserSession userSession;
    private User user;

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
                getUserApi().
                login(token).
                enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        user = response.body();

                        if (user != null) {
                            userSession.saveCredentials(user, token);
                            loginSuccesfull();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
    }

    private void loginSuccesfull() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
