package com.example.githubclient.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepositoryFile {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("path")
    @Expose
    private String path;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("download_url")
    @Expose
    private String downloadUrl;

    @SerializedName("size")
    @Expose
    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFile(){
        return this.type.equals("file");
    }

    @Override
    public String toString() {
        return "RepositoryFile{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type='" + type + '\'' +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", size=" + size +
                '}';
    }
}
