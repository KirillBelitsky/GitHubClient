package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubclient.R;
import com.example.githubclient.model.Repository;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.ui.adapter.RepositoryListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RepositoryListAdapter adapter;
    private List<Repository> repositoryList;
    private LinearLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repository, container, false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        getRepositories();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view);
    }

    private void getRepositories() {
        NetworkService.getInstance()
                .getRepoApi()
                .getStarredRepoByLogin(getArguments().getString("login"))
                .enqueue(new Callback<List<Repository>>() {
                    @Override
                    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
                        repositoryList = response.body();
                        if (repositoryList == null) {
                            System.out.println("111");
                        } else {
                            for (Repository temp : repositoryList)
                                System.out.println(temp.toString());
                        }

                        adapter = new RepositoryListAdapter(getContext(),repositoryList);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLayoutManager);
                    }

                    @Override
                    public void onFailure(Call<List<Repository>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
