package com.example.githubclient.network.service;

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


public class UserService extends AsyncTask<String, Void, String> {

    private String json;
    public static final String GET_USER = "https://api.github.com/users/%s";
    public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection = null;
        String urlFormat = String.format(GET_USER,strings[0]);

        try {

            url = new URL(urlFormat);
            urlConnection = (HttpURLConnection) url.openConnection();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                json = JsonReader.readStream(urlConnection.getInputStream());
                return json;
            }

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
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }

}
