package com.example.myweatherapp.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.example.myweatherapp.R;
import com.example.myweatherapp.data.Currently;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DarkSkyWeather {
    private Context mContext;
    private WeatherServiceCallback callback;
    private String[] location;
    private String key;
    private Exception error;
    //constructor with callback
    public DarkSkyWeather(WeatherServiceCallback callback, Context c){
        this.mContext = c;
        this.callback = callback;
    }

    public String[] getLocation(){
        return location;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshWeather(final String[] location){
        new AsyncTask<String[], Void, String>(){
            @Override
            protected String doInBackground(String[]... strings) {
                String endpoint = "https://api.darksky.net/forecast/" +
                        mContext.getString(R.string.secret_key) +"/" +
                        location[0] + "," + location[1];
                //try catch for opening connection to API endpoint
                try {
                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    //new buffered reader for input stream from API endpoint
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    //get result in string form
                    StringBuilder result = new StringBuilder();
                    String line;
                    //loop through recording lines until end of API response
                    while((line = reader.readLine()) != null){
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                    e.printStackTrace();
                }
                return null;
            }
            //member to prepare weather data after retrieval from API
            @Override
            protected void onPostExecute(String s){
                if(s == null && error != null){
                    //exception while connecting to API endpoint
                    callback.serviceFailure(error);
                }
                try {
                    //get current weather data from query results JSON
                    JSONObject data = new JSONObject(s);
                    Currently currently = new Currently();
                    currently.populate(data.optJSONObject("currently"));
                    //callback success
                    callback.serviceSuccess(currently);
                } catch (JSONException e) {
                    //failure while retrieving current weather data from API
                    callback.serviceFailure(e);
                    e.printStackTrace();
                }
            }
        }.execute(location);
    }
}
