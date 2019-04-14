package com.example.githubclient.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.network.NetworkService;
import com.example.githubclient.network.model.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        textView = (TextView) findViewById(R.id.textView2);

        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithID("KirillBelitsky")
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                        Post post = response.body();

                        textView.append(post.getId() + "\n");
                        textView.append(post.getLogin()  + "\n");
                        textView.append(post.getName() + "\n");
                    }

                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                        textView.append("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}
