package com.example.githubclient.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.githubclient.R;

import static com.example.githubclient.constants.Constants.FOLLOWERS;
import static com.example.githubclient.constants.Constants.USERNAME;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView followers;
    private TextView following;
    private TextView email;
    private TextView company;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) view.findViewById(R.id.profile_username);
        followers = (TextView)view.findViewById(R.id.profile_followers);
        following = (TextView)view.findViewById(R.id.profile_following);
        email = (TextView)view.findViewById(R.id.profile_email);
        company = (TextView)view.findViewById(R.id.profile_company);

        getDataFromBundle();

        return view;
    }

    private void getDataFromBundle(){
        try {

            name.setText(getArguments().getString("username"));
            followers.setText(String.valueOf(getArguments().getInt("followers")));
            following.setText(String.valueOf(getArguments().getInt("following")));
            email.setText(getArguments().getString("email","empty"));
            company.setText(getArguments().getString("company","empty"));

        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
