package com.example.githubclient.network.service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.githubclient.network.response.AsyncResponse;
import com.example.githubclient.util.reader.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class CodeLoader extends AsyncTask<String, Void, String> {

    private String content = null;
    public AsyncResponse delegate;

    public CodeLoader(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;
        String urlFormat = strings[0];

        try {
            url = new URL(urlFormat);
            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
               content = JsonReader.readStream(urlConnection.getInputStream());
            }

            return content;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String content) {
        delegate.processFinish(content);
    }

}
