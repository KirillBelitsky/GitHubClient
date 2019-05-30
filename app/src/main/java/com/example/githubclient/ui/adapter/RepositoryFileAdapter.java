package com.example.githubclient.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.RepositoryFile;
import com.example.githubclient.ui.adapter.listener.OnItemClickListener;

import java.util.List;

public class RepositoryFileAdapter extends RecyclerView.Adapter<RepositoryFileAdapter.ViewHolder> {

    private List<RepositoryFile> files;
    private OnItemClickListener<RepositoryFile> clickListener;

    public RepositoryFileAdapter(List<RepositoryFile> files, OnItemClickListener<RepositoryFile> clickListener){
        this.files = files;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RepositoryFileAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_repo_file,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(files.get(i),clickListener);
    }

    @Override
    public int getItemCount() {
        return files.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name, size;
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.repo_file_name);
            size = itemView.findViewById(R.id.repo_file_size);
            image = itemView.findViewById(R.id.repo_file_image);
        }

        private void bind(final RepositoryFile file, final OnItemClickListener<RepositoryFile> clickListener){
            name.setText(file.getName());
            size.setText(file.getSize() + "B");

            if(file.isFile())
                image.setImageResource(R.drawable.ic_file);
            else image.setImageResource(R.drawable.ic_folder);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(file);
                }
            });
        }
    }
}
