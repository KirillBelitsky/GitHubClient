package com.example.githubclient.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.Repository;

import java.util.List;

public class RepositoryListAdater extends RecyclerView.Adapter<RepositoryListAdater.ViewHolder> {

    private List<Repository> repositoryList;

    public RepositoryListAdater(List<Repository> repositories){
        this.repositoryList = repositories;
    }

    @NonNull
    @Override
    public RepositoryListAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_repository,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryListAdater.ViewHolder holder, int position) {
        Repository repository = repositoryList.get(position);
        holder.nameView.setText(repository.getName());
        holder.languageView.setText(repository.getLanguage());
        holder.descriptionView.setText(repository.getDescription());
        holder.starView.setText(repository.getStarCount());
        holder.forkView.setText(repository.getForkCount());
        holder.ownerView.setText(repository.getOwner().getLogin());
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;
        private final TextView nameView, descriptionView,languageView;
        private final TextView starView,forkView,ownerView;

        ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_user_avatar);
            nameView = (TextView) view.findViewById(R.id.tv_repo_name);
            descriptionView = (TextView) view.findViewById(R.id.tv_repo_description);
            languageView = (TextView) view.findViewById(R.id.tv_language);
            starView = (TextView) view.findViewById(R.id.tv_star_num);
            forkView = (TextView) view.findViewById(R.id.tv_fork_num);
            ownerView = (TextView) view.findViewById(R.id.tv_owner_name);
        }

    }

}
