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
import com.example.githubclient.model.User;
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private OnItemClickListener<User> clickListener;
    private Context context;

    public UserAdapter(List<User> users,Context context, OnItemClickListener<User> clickListener) {
        this.users = users;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_user,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(users.get(position),clickListener);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView login,name;
        private final ImageView image;

        ViewHolder(View itemView){
            super(itemView);

            login = itemView.findViewById(R.id.search_user_login);
            name = itemView.findViewById(R.id.search_user_name);
            image = itemView.findViewById(R.id.search_user_image);
        }

        public void bind(final User user, final OnItemClickListener<User> clickListener){
            Picasso.with(context).load(user.getAvatarUrl()).transform(new CircularTransformation()).into(image);
            login.setText(user.getLogin());
            name.setText(user.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(user);
                }
            });
        }
    }
}
