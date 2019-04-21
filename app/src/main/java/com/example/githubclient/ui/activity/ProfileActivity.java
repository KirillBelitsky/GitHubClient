package com.example.githubclient.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.githubclient.R;
import com.example.githubclient.ui.fragment.ProfileFragment;

public class ProfileActivity extends FragmentActivity {

    private Fragment profileFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileFragment = new ProfileFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.profile_container)!=null) {
            fragmentManager.beginTransaction()
                    .add(R.id.profile_container, profileFragment)
                    .commit();
        }else
            System.out.println("kykykkykyky");
    }
}
