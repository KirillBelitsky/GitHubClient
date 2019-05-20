package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.githubclient.R;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.model.User;
import com.example.githubclient.network.session.UserSession;
import com.unstoppable.submitbuttonview.SubmitButton;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String USERNAME_REGEX = "^[a-zA-Z0-9]{1,38}\\$";
    private static final String PASSWORD_REGEX = "^(?=.*?[A-Za-z])(?=.*?[0-9]).{8,}\\$";

    private TextInputEditText mUsername;
    private TextInputLayout mUsernameLayout;
    private TextInputEditText mPassword;
    private TextInputLayout mPasswordLayout;
    private SubmitButton loginButton;
    private ImageView imageView;

    private UserSession userSession;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login_bn);
        mUsername = findViewById(R.id.user_name_et);
        mPassword = findViewById(R.id.password_et);
        mUsernameLayout = findViewById(R.id.user_name_layout);
        mPasswordLayout = findViewById(R.id.password_layout);
        imageView = findViewById(R.id.loginImage);

        userSession = new UserSession(getApplicationContext());

    }


    public void onLoginClick(View view) {

        if(loginCheck()) {
            final String token = userSession.createToken(mUsername.getText().toString(), mPassword.getText().toString());
            loginButton.setEnabled(false);

            NetworkService.getInstance().
                    getUserApi().
                    login(token).
                    enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            user = response.body();

                            if (user != null) {
                                userSession.saveCredentials(user, token);

                                loginButton.doResult(true);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                    }
                                }, 5000);
                                loginSuccesfull();
                            }

                            loginButton.doResult(false);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    loginButton.reset();
                                    loginButton.setEnabled(true);
                                }
                            }, 1000);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                        }
                    });
        }
    }

    private void loginSuccesfull() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private boolean loginCheck(){
        boolean valid = true;
        String userName = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        if(StringUtils.isBlank(userName)){
            valid = false;
            mUsernameLayout.setError(getString(R.string.username_invalid));

        }else{
            mUsernameLayout.setErrorEnabled(false);
        }
        if(StringUtils.isBlank(password)){
            valid = false;
            mPasswordLayout.setError(getString(R.string.password_invalid));
        }else{
            mPasswordLayout.setErrorEnabled(false);
        }
        if(!valid)
            loginButton.reset();

        return valid;
    }

}
