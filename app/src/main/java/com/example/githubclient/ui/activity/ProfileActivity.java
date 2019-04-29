package com.example.githubclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.githubclient.R;
import com.example.githubclient.network.model.User;
import com.example.githubclient.network.response.AsyncResponse;
import com.example.githubclient.network.service.UserService;
import com.example.githubclient.ui.fragment.ProfileFragment;
import com.example.githubclient.util.parser.JsonParser;

import java.util.concurrent.ExecutionException;

import static com.example.githubclient.constants.Constants.LOGIN;

public class ProfileActivity extends FragmentActivity implements AsyncResponse {

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

        bundle = new Bundle();
        profileFragment = new ProfileFragment();
        userService = new UserService();
        userService.delegate = this;

        login = getIntent().getStringExtra(LOGIN);
        userService.execute(login);

        processFinish(json);
        user = JsonParser.parseUser(json);

        bundle.putString("username",user.getName());
        bundle.putString("email",user.getEmail());
        bundle.putString("company",user.getCompany());
        bundle.putInt("following",user.getFollowing());
        bundle.putInt("followers",user.getFollowers());

        profileFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.profile_container,profileFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void processFinish(String result) {
        try {
            this.json = userService.get();
        }catch (InterruptedException e){
            e.printStackTrace();
        }catch (ExecutionException e){
            e.printStackTrace();
        }
    }
}
