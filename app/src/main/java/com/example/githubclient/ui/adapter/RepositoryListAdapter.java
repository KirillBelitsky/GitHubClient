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
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.ViewHolder> {

    private List<Repository> repositoryList;
    private Context context;
    private OnItemClickListener<Repository> clickListener;

    public RepositoryListAdapter(Context context,List<Repository> repositories,OnItemClickListener<Repository> clickListener){
        this.context = context;
        this.repositoryList = repositories;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RepositoryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_repository,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RepositoryListAdapter.ViewHolder holder, int position) {
        holder.bind(repositoryList.get(position), clickListener);
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
            imageView = view.findViewById(R.id.iv_user_avatar);
            nameView = view.findViewById(R.id.tv_repo_name);
            descriptionView = view.findViewById(R.id.tv_repo_description);
            languageView = view.findViewById(R.id.tv_language);
            starView = view.findViewById(R.id.tv_star_num);
            forkView = view.findViewById(R.id.tv_fork_num);
            ownerView = view.findViewById(R.id.tv_owner_name);
        }

        private void bind(final Repository repository, final OnItemClickListener<Repository> clickListener){
            Picasso.with(context).load(repository.getOwner().getAvatarUrl()).transform(new CircularTransformation()).into(imageView);
            nameView.setText(repository.getName());
            languageView.setText(repository.getLanguage());
            descriptionView.setText(repository.getDescription());
            starView.setText(String.valueOf(repository.getStarCount()));
            forkView.setText(String.valueOf(repository.getForkCount()));
            ownerView.setText(repository.getOwner().getLogin());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(repository);
                }
            });
        }

    }

}
