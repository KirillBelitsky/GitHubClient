package com.example.githubclient.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.githubclient.R;
import com.example.githubclient.model.Issue;

import java.text.SimpleDateFormat;
import java.util.List;

public class IssueListAdapter extends RecyclerView.Adapter<IssueListAdapter.ViewHolder> {

    private List<Issue> issues;
    private Context context;

    public IssueListAdapter(List<Issue> issues, Context context) {
        this.issues = issues;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_issue,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int positon) {
        Issue issue = issues.get(positon);
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        viewHolder.title.setText(issue.getTitle());
        viewHolder.body.setText(issue.getBody());
        viewHolder.state.setText(issue.getState());
        viewHolder.commentsCount.setText(String.valueOf(issue.getComments()));
        viewHolder.date.setText(format.format(issue.getDate()));
    }

    @Override
    public int getItemCount() {
        return issues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView title, body,commentsCount,date,state;

        ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.item_issue_title);
            body = view.findViewById(R.id.item_issue_body);
            commentsCount = view.findViewById(R.id.item_issue_commentsCount);
            date = view.findViewById(R.id.item_issue_date);
            state = view.findViewById(R.id.item_issue_state);
        }
    }
}
