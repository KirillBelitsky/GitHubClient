package com.example.githubclient.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.githubclient.ui.fragment.ProfileFragment;
import com.example.githubclient.ui.fragment.RepositoryFragment;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private Bundle bundle;

    public SimpleFragmentPagerAdapter(FragmentManager fm, Context context,Bundle bundle) {
        super(fm);
        this.context = context;
        this.bundle = bundle;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        if(i == 0)
            fragment = new ProfileFragment();
        else
            bundle.putString("repo","own");
            fragment = new RepositoryFragment();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Profile";
        else return "Repositories";
    }
}
