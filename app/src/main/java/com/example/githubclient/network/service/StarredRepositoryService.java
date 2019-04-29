package com.example.githubclient.network.service;

import android.os.AsyncTask;

import com.example.githubclient.network.response.AsyncResponse;
import com.example.githubclient.util.reader.JsonReader;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StarredRepositoryService extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate = null;
    private String json;
    private static final String GET_STARRED_REPO = "https://api.github.com/users/%s/starred";

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection connection = null;

        try {
            url = new URL(String.format(GET_STARRED_REPO, strings[0]));
            connection = (HttpURLConnection) url.openConnection();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                json = JsonReader.readStream(connection.getInputStream());
                return json;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
