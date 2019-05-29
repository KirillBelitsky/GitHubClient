package com.example.githubclient.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.Follower;
import com.example.githubclient.model.User;
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {

    private List<Follower> followers;
    private Context context;
    private OnItemClickListener<Follower> clickListener;

    public FollowersAdapter(List<Follower> followers, OnItemClickListener<Follower> clickListener) {
        this.followers = followers;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FollowersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_follow,viewGroup,false);
        return new FollowersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowersAdapter.ViewHolder viewHolder, int i) {
        viewHolder.bind(followers.get(i),clickListener);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView login;
        private final ImageView image;

        ViewHolder(View itemView){
            super(itemView);

            login = itemView.findViewById(R.id.follow_login);
            image = itemView.findViewById(R.id.follow_image);
        }

        public void bind(final Follower follower, final OnItemClickListener<Follower> clickListener){
            Picasso.with(context).load(follower.getAvatar_url()).transform(new CircularTransformation()).into(image);
            login.setText(follower.getLogin());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(follower);
                }
            });
        }
    }
}
