package com.example.githubclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.githubclient.R;
import com.example.githubclient.network.model.User;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.network.service.UserService;
import com.example.githubclient.network.session.UserSession;
import com.example.githubclient.ui.fragment.ProfileFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.USERNAME;

public class ProfileActivity extends FragmentActivity {

    private Fragment profileFragment;
    private String login;
    private User user;
    private UserService userService;
    private Bundle bundle;
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userService = new UserService();

        System.out.println("kkyky");
        login = getIntent().getStringExtra(LOGIN);
        System.out.println(login);
        user = userService.getUserByLogin(login);

        System.out.println("ugu");

        bundle.putString("username",user.getName());
        bundle.putString("email",user.getEmail());
        bundle.putString("company",user.getCompany());
        bundle.putInt("following",user.getFollowing());
        bundle.putInt("followers",user.getFollowers());

        profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.profile_container,profileFragment);
        fragmentTransaction.commit();

    }

}
