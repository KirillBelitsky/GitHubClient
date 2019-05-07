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
import com.example.githubclient.util.circleTransform.CircularTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

    private List<User> users;
    private Context context;

    public UserListAdapter(List<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_user,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder viewHolder, int position) {
        User user = users.get(position);

        Picasso.with(context).load(user.getAvatarUrl()).transform(new CircularTransformation()).into(viewHolder.image);
        viewHolder.login.setText(user.getLogin());
        viewHolder.name.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView login,name;
        private final ImageView image;

        ViewHolder(View view){
            super(view);

            login = view.findViewById(R.id.search_user_login);
            name = view.findViewById(R.id.search_user_name);
            image = view.findViewById(R.id.user_search_view);
        }
    }
}
