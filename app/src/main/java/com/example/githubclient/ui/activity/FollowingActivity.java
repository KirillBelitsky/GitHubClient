package com.example.githubclient.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.githubclient.R;
import com.example.githubclient.model.Follower;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.ui.adapter.FollowersAdapter;
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.FOLLOWERS;
import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.TOKEN;

public class FollowingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.setTitle("Following");

        progressBar = findViewById(R.id.followers_list_progress_bar);
        recyclerView = findViewById(R.id.recycler_view_followers);
        layoutManager = new LinearLayoutManager(this);
        preferences = getSharedPreferences("authUser", Context.MODE_PRIVATE);

        if (getIntent().getStringExtra(FOLLOWERS).equals(preferences.getString(LOGIN, "")))
            loadOwnFollowing();
        else loadFollowing();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    private void loadOwnFollowing() {
        final Activity activity = this;

        NetworkService.getInstance()
                .getFollowApi()
                .getOwnFollowing(preferences.getString(TOKEN, ""))
                .enqueue(new Callback<List<Follower>>() {
                    @Override
                    public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                        if (response.body() != null) {
                            System.out.println(response.body());
                            recyclerView.setAdapter(new FollowersAdapter(response.body(), new OnItemClickListener<Follower>() {
                                @Override
                                public void onItemClick(Follower item) {
                                    Intent intent = new Intent(activity, ProfileActivity.class);
                                    intent.putExtra(LOGIN, item.getLogin());
                                    startActivity(intent);
                                }
                            }));

                            recyclerView.setLayoutManager(layoutManager);
                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Follower>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }

    private void loadFollowing() {
        final Activity activity = this;

        NetworkService.getInstance()
                .getFollowApi()
                .getUserFollowing(getIntent().getStringExtra(FOLLOWERS))
                .enqueue(new Callback<List<Follower>>() {
                    @Override
                    public void onResponse(Call<List<Follower>> call, Response<List<Follower>> response) {
                        if (response.body() != null) {
                            recyclerView.setAdapter(new FollowersAdapter(response.body(), new OnItemClickListener<Follower>() {
                                @Override
                                public void onItemClick(Follower item) {
                                    Intent intent = new Intent(activity, ProfileActivity.class);
                                    intent.putExtra(LOGIN, item.getLogin());
                                    startActivity(intent);
                                }
                            }));

                            recyclerView.setLayoutManager(layoutManager);
                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Follower>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
