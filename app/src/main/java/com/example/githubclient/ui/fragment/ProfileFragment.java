package com.example.githubclient.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.FOLLOWERS;
import static com.example.githubclient.constants.Constants.USERNAME;

public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView followers;
    private TextView following;
    private TextView email;
    private TextView company;
    private ImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = (TextView) view.findViewById(R.id.profile_username);
        followers = (TextView)view.findViewById(R.id.profile_followers);
        following = (TextView)view.findViewById(R.id.profile_following);
        email = (TextView)view.findViewById(R.id.profile_email);
        company = (TextView)view.findViewById(R.id.profile_company);
        profileImage = (ImageView) view.findViewById(R.id.profie_image);

        getDataFromBundle();

        return view;
    }

    private void getDataFromBundle(){
        NetworkService.getInstance()
                .getUserApi()
                .getUserByLogin(getArguments().get("login").toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Picasso.with(getContext()).load(response.body().getAvatarUrl()).transform(new CircularTransformation()).into(profileImage);
                        name.setText(response.body().getName());
                        followers.setText(String.valueOf(response.body().getFollowers()));
                        following.setText(String.valueOf(response.body().getFollowing()));
                        email.setText(response.body().getEmail());
                        company.setText(response.body().getCompany());
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
