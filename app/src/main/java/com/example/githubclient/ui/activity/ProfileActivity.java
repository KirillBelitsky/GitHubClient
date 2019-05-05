package com.example.githubclient.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.response.AsyncResponse;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.network.service.UserService;
import com.example.githubclient.ui.fragment.ProfileFragment;
import com.example.githubclient.util.parser.JsonParser;

import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;

public class ProfileActivity extends AppCompatActivity {

    private Fragment profileFragment;
    private String login;
    private User user;
    private UserService userService;
    private Bundle bundle;
    private String json;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bundle = new Bundle();
        profileFragment = new ProfileFragment();

        login = getIntent().getStringExtra(LOGIN);

        bundle.putString("login",login);
        profileFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.profile_container,profileFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.profile_edit) {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == android.R.id.home)
            finish();

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_opp, menu);
        return true;
    }

}
