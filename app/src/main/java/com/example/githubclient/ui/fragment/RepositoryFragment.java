package com.example.githubclient.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.githubclient.R;
import com.example.githubclient.model.Repository;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.ui.activity.RepositoryActivity;
import com.example.githubclient.ui.adapter.RepositoryListAdapter;
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.TOKEN;

public class RepositoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RepositoryListAdapter adapter;
    private List<Repository> repositoryList;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_repository, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        preferences = getActivity().getSharedPreferences("authUser", Context.MODE_PRIVATE);

        if(getArguments().get("repo").equals("starred")){
            getActivity().setTitle("Starred repositories");
            getStarredRepositories();
        }
        else{
            getActivity().setTitle("Repositories");
            if(getArguments().get(LOGIN).equals(preferences.getString(LOGIN,"")))
                getOwnRepositories();
            else
                getRepositories();
        }

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.repo_list_progress_bar);
    }

    private void getStarredRepositories() {
        NetworkService.getInstance()
                .getRepoApi()
                .getStarredRepoByLogin(getArguments().getString(LOGIN))
                .enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        repositoryList = response.body();

                        adapter = new RepositoryListAdapter(getContext(), repositoryList, new OnItemClickListener<Repository>() {
                            @Override
                            public void onItemClick(Repository item) {
                                Intent intent = new Intent(getActivity(),RepositoryActivity.class);
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLayoutManager);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }

    private void getOwnRepositories(){
        NetworkService.getInstance()
                .getRepoApi()
                .getOwnRepositories(preferences.getString(TOKEN,""))
                .enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        repositoryList = response.body();

                        adapter = new RepositoryListAdapter(getContext(),repositoryList, new OnItemClickListener<Repository>() {
                            @Override
                            public void onItemClick(Repository item) {
                                Intent intent = new Intent(getActivity(), RepositoryActivity.class);
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLayoutManager);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }

    private void getRepositories(){
        NetworkService.getInstance()
                .getRepoApi()
                .getRepositoriesByLogin(getArguments().getString(LOGIN))
                .enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        repositoryList = response.body();

                        adapter = new RepositoryListAdapter(getContext(),repositoryList, new OnItemClickListener<Repository>() {
                            @Override
                            public void onItemClick(Repository item) {
                                Intent intent = new Intent(getActivity(),RepositoryActivity.class);
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLayoutManager);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }

    public void changeToStarred(){
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setTitle("Starred repositories");
        getArguments().putString("repo","starred");

        getStarredRepositories();
    }

    public void changeToOwn(){
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        getActivity().setTitle("My repositories");
        getArguments().putString("repo","own");

        getOwnRepositories();
    }
}
