package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;


public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView login;
    private TextView followers;
    private TextView following;
    private TextView email;
    private TextView createdAt;
    private LinearLayout emailHeader;
    private TextView company;
    private LinearLayout companyHeader;
    private ImageView profileImage;
    private ProgressBar progressBar;
    private RelativeLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.profile_username);
        login = view.findViewById(R.id.profile_login);
        followers = view.findViewById(R.id.profile_followers);
        following = view.findViewById(R.id.profile_following);
        createdAt = view.findViewById(R.id.profile_created_at);
        email = view.findViewById(R.id.profile_email);
        emailHeader = view.findViewById(R.id.profile_email_header);
        company = view.findViewById(R.id.profile_company);
        companyHeader = view.findViewById(R.id.profile_company_header);
        profileImage = view.findViewById(R.id.profie_image);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        layout = view.findViewById(R.id.profile_fragment);

        layout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        getData();
        getActivity().setTitle("Profile");

        return view;
    }


    private void getData(){
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);

        NetworkService.getInstance()
                .getUserApi()
                .getUserByLogin(getArguments().get(LOGIN).toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Picasso.with(getContext()).load(response.body().getAvatarUrl()).transform(new CircularTransformation()).into(profileImage);

                        login.setText(response.body().getLogin());
                        name.setText(response.body().getName());
                        createdAt.append(" " + format.format(response.body().getDate()));
                        followers.setText(String.valueOf(response.body().getFollowers()));
                        following.setText(String.valueOf(response.body().getFollowing()));

                        if (response.body().getEmail() == null) {
                            emailHeader.setVisibility(View.GONE);
                        }
                        else email.setText(response.body().getEmail()
                        );

                        if (response.body().getCompany() == null) {
                            companyHeader.setVisibility(View.GONE);
                        }
                        else company.setText(response.body().getCompany());

                        progressBar.setVisibility(View.INVISIBLE);
                        layout.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
