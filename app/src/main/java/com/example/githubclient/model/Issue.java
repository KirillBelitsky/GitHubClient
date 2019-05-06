package com.example.githubclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Issue {

    @Expose
    private String title;

    @Expose
    private String state;

    @SerializedName("updated_at")
    @Expose
    private Date date;

    private String body;

    @Expose
    private int comments;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "title='" + title + '\'' +
                ", state='" + state + '\'' +
                ", date='" + date + '\'' +
                ", body='" + body + '\'' +
                ", comments=" + comments +
                '}';
    }
}
