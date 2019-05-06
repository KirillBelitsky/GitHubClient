package com.example.githubclient.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubclient.R;
import com.example.githubclient.model.Repository;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.ViewHolder> {

    private List<Repository> repositoryList;
    private Context context;

    public RepositoryListAdapter(Context context,List<Repository> repositories){
        this.context = context;
        this.repositoryList = repositories;
    }

    @NonNull
    @Override
    public RepositoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_repository,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryListAdapter.ViewHolder holder, int position) {
        Repository repository = repositoryList.get(position);
        Picasso.with(context).load(repository.getOwner().getAvatarUrl()).transform(new CircularTransformation()).into(holder.imageView);
        holder.nameView.setText(repository.getName());
        holder.languageView.setText(repository.getLanguage());
        holder.descriptionView.setText(repository.getDescription());
        holder.starView.setText(String.valueOf(repository.getStarCount()));
        holder.forkView.setText(String.valueOf(repository.getForkCount()));
        holder.ownerView.setText(repository.getOwner().getLogin());
    }

    @Override
    public int getItemCount() {
        return repositoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"gdfg",Toast.LENGTH_SHORT).show();
        }
    }

}
