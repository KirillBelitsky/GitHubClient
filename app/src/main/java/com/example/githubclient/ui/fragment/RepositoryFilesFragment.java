package com.example.githubclient.ui.fragment;

import android.content.Intent;
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
import com.example.githubclient.model.RepositoryFile;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.ui.activity.CodeViewActivity;
import com.example.githubclient.ui.adapter.RepositoryFileAdapter;
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.LOGIN;
import static com.example.githubclient.constants.Constants.REPOSITORY;

public class RepositoryFilesFragment extends Fragment{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private List<RepositoryFile> files;
    private String path = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_files,container,false);

        layoutManager = new LinearLayoutManager(getActivity());

        loadData(path);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view_repo_files);
    }

    private void loadData(final String path){
        NetworkService.getInstance()
                .getRepoApi()
                .getRepoFiles(getArguments().getString(LOGIN),getArguments().getString(REPOSITORY),path)
                .enqueue(new Callback<List<RepositoryFile>>() {
                    @Override
                    public void onResponse(Call<List<RepositoryFile>> call, Response<List<RepositoryFile>> response) {
                        if(response.body() != null){
                            files = response.body();

                            Collections.sort(files, new Comparator<RepositoryFile>() {
                                @Override
                                public int compare(RepositoryFile o1, RepositoryFile o2) {
                                    if(!o1.isFile() && o2.isFile()){
                                        return -1;
                                    }
                                    else if(o1.isFile() && !o2.isFile()){
                                        return 1;
                                    }
                                    else return o1.getName().compareTo(o2.getName());
                                }
                            });

                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(new RepositoryFileAdapter(files, new OnItemClickListener<RepositoryFile>() {
                                @Override
                                public void onItemClick(RepositoryFile item) {
                                    if(item.getType().equals("file")) {
                                        Intent intent = new Intent(getActivity(),CodeViewActivity.class);
                                        intent.putExtra("downloadUrl",item.getDownloadUrl());
                                        intent.putExtra("title",item.getName());
                                        startActivity(intent);
                                    }
                                    else loadData(item.getPath());
                                }
                            }));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<RepositoryFile>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }

}
