package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.githubclient.model.Repository;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.REPOSITORY;

public class RepositoryInfoFragment extends Fragment {

    private TextView name;
    private TextView login;
    private TextView stars;
    private TextView forks;
    private TextView descr;
    private TextView lang;
    private TextView createdAt;
    private TextView lastUpdate;
    private ImageView image;
    private ProgressBar progressBar;
    private LinearLayout descrHeader;
    private RelativeLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_info,container,false);

        name = view.findViewById(R.id.repo_info_name);
        login = view.findViewById(R.id.repo_info_owner);
        stars = view.findViewById(R.id.repo_info_stars);
        forks = view.findViewById(R.id.repo_info_forks);
        descr = view.findViewById(R.id.repo_info_descr);
        lang = view.findViewById(R.id.repo_info_lang);
        createdAt = view.findViewById(R.id.repo_info_created_at);
        lastUpdate = view.findViewById(R.id.repo_info_last_update);
        image = view.findViewById(R.id.repo_info_image);
        progressBar = view.findViewById(R.id.repo_info_progress_bar);
        descrHeader = view.findViewById(R.id.repo_info_descr_header);
        layout = view.findViewById(R.id.repo_info_fragment);

        layout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        loadData();

        return view;
    }

    private void loadData(){
        final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.ROOT);

        NetworkService.getInstance()
                .getRepoApi()
                .getRepository(getArguments().getString(LOGIN),getArguments().getString(REPOSITORY))
                .enqueue(new Callback<Repository>() {
                    @Override
                    public void onResponse(Call<Repository> call, Response<Repository> response) {
                        if(response.body()!=null){
                            Picasso.with(getContext()).load(response.body().getOwner().getAvatarUrl())
                                    .transform(new CircularTransformation()).into(image);

                            name.setText(response.body().getName());
                            login.setText(response.body().getOwner().getLogin());
                            stars.setText(String.valueOf(response.body().getStarCount()));
                            forks.setText(String.valueOf(response.body().getForkCount()));
                            lang.setText(response.body().getLanguage());

                            createdAt.append(" " + format.format(response.body().getCreatedAt()));
                            lastUpdate.append(" " + format.format(response.body().getUpdatedAt()));

                            if (response.body().getDescription() == null) {
                                descrHeader.setVisibility(View.GONE);
                            }
                            else descr.setText(response.body().getDescription());

                            progressBar.setVisibility(View.INVISIBLE);
                            layout.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Repository> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
