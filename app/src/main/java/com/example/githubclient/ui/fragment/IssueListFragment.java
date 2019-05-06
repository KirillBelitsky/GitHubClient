package com.example.githubclient.ui.fragment;

import android.content.Context;
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
import com.example.githubclient.model.Issue;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.ui.adapter.IssueListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.githubclient.constants.Constants.TOKEN;

public class IssueListFragment extends Fragment {

    private IssueListAdapter adapter;
    private List<Issue> issues;
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private SharedPreferences preferences;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_issue,container,false);

        mLayoutManager = new LinearLayoutManager(getActivity());
        getActivity().setTitle("Issues");
        preferences = getActivity().getSharedPreferences("authUser", Context.MODE_PRIVATE);

        loadData();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.recycler_view_issues);
        progressBar = view.findViewById(R.id.issue_list_progress_bar);
    }

    private void loadData(){
        NetworkService.getInstance()
                .getIssueApi()
                .getIssues(preferences.getString(TOKEN,""),"all","all")
                .enqueue(new Callback<List<Issue>>() {
                    @Override
                    public void onResponse(Call<List<Issue>> call, Response<List<Issue>> response) {
                        issues = response.body();

                        adapter = new IssueListAdapter(response.body(),getContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(mLayoutManager);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onFailure(Call<List<Issue>> call, Throwable t) {
                        call.cancel();
                    }
                });
    }
}
