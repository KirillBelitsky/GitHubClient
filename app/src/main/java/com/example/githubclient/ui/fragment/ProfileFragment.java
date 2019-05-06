package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;


public class ProfileFragment extends Fragment {

    private TextView name;
    private TextView followers;
    private TextView following;
    private TextView repo;
    private TextView email;
    private LinearLayout emailHeader;
    private TextView company;
    private LinearLayout companyHeader;
    private ImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name = view.findViewById(R.id.profile_username);
        followers = view.findViewById(R.id.profile_followers);
        following = view.findViewById(R.id.profile_following);
        repo = view.findViewById(R.id.profile_repo);
        email = view.findViewById(R.id.profile_email);
        emailHeader = view.findViewById(R.id.profile_email_header);
        company = view.findViewById(R.id.profile_company);
        companyHeader = view.findViewById(R.id.profile_company_header);
        profileImage = view.findViewById(R.id.profie_image);

        getDataFromBundle();

        return view;
    }


    private void getDataFromBundle(){
        NetworkService.getInstance()
                .getUserApi()
                .getUserByLogin(getArguments().get(LOGIN).toString())
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Picasso.with(getContext()).load(response.body().getAvatarUrl()).transform(new CircularTransformation()).into(profileImage);
                        name.setText(response.body().getName());
                        followers.setText(String.valueOf(response.body().getFollowers()));
                        following.setText(String.valueOf(response.body().getFollowing()));
                        repo.setText(String.valueOf(response.body().getCountRepo()));

                        if (response.body().getEmail() != null) {
                            emailHeader.setVisibility(View.VISIBLE);
                            email.setText(response.body().getEmail());
                        }

                        if (response.body().getCompany() != null) {
                            companyHeader.setVisibility(View.VISIBLE);
                            System.out.println("11111" + response.body().getCompany());
                            company.setText(response.body().getCompany());
                        }

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
