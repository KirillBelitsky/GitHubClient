package com.example.githubclient.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.githubclient.R;
import com.example.githubclient.model.User;
import com.example.githubclient.network.service.NetworkService;
import com.example.githubclient.ui.activity.SearchActivity;
import com.example.githubclient.ui.adapter.UserListAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserFragment extends Fragment {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private LinearLayoutManager layoutManager;
    private ProgressBar progressBar;
    private UserListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_user,container,false);

        getActivity().setTitle("Search");

        layoutManager = new LinearLayoutManager(getActivity());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerView = view.findViewById(R.id.search_user_recycler_view);
        searchView = view.findViewById(R.id.user_search_view);
        progressBar = view.findViewById(R.id.search_progress_bar);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String text) {
                progressBar.setVisibility(View.VISIBLE);
                searchView.setEnabled(false);

                loadUsers(text);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String text) {
                Toast.makeText(getContext(), "Result: "+ text, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

//        searchView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                searchView.setIconified(false);
//            }
//        });
    }

    private void loadUsers(String text){
        String query = String.format("%s in:login",text);
        System.out.println(query);

        NetworkService.getInstance()
                .getUserApi()
                .searchUsers(query,"1","5")
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        System.out.println("1111");
                        adapter = new UserListAdapter(response.body(),getContext());
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);

                        searchView.setEnabled(true);
                        progressBar.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        System.out.println("242344");
                        call.cancel();
                    }
                });
        System.out.println("22222222");
    }

}
