package com.example.githubclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.service.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        textView = findViewById(R.id.textView2);

        NetworkService.getInstance()
                .getUserApi()
                .getUserByLogin("KirillBelitsky")
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        User user = response.body();

                        textView.append(user.getId() + "\n");
                        textView.append(user.getLogin()  + "\n");
                        textView.append(user.getName() + "\n");
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {

                        textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}
