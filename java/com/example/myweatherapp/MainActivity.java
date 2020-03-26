package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myweatherapp.data.Currently;
import com.example.myweatherapp.service.DarkSkyWeather;
import com.example.myweatherapp.service.WeatherServiceCallback;

public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private static String[] location = {   //location string array
            "45.418302",    //index 0, latitude
            "-122.846298"   //index 1, longitude
    };

    private DarkSkyWeather service;
    //private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView = (ImageView) findViewById(R.id.weatherImageView);
        temperatureTextView = (TextView) findViewById(R.id.temperatureTextView);
        conditionTextView = (TextView) findViewById(R.id.conditionTextView);
        locationTextView = (TextView) findViewById(R.id.locationTextView);

        //create DarkSkyWeatherService w/ callback and application context
        service = new DarkSkyWeather(this, this.getApplicationContext());
        //request current weather data for provided latitude/longitude
        service.refreshWeather(location);
    }
    
    @Override
    public void serviceSuccess(Currently currently) {
        //set location text using latitude and longitude
        String locationText = "Latitude: " + location[0] + ",   Longitude: " + location[1];
        locationTextView.setText(locationText);
        //set condition text based on summary from API
        conditionTextView.setText(currently.getSummary());
        //set temperature text based on value from API
        String tempText = currently.getTemperature() + "\u2109";
        temperatureTextView.setText(tempText);

        //set Icon ImageView for weather, according to icon description from API
        Drawable weatherIcon;
        switch(currently.getIcon()){
            case "clear-day":
                weatherIcon = getDrawable(R.drawable.icon_32);
                break;
            case "clear-night":
                weatherIcon = getDrawable(R.drawable.icon_31);
                break;
            case "rain":
                weatherIcon = getDrawable(R.drawable.icon_11);
                break;
            case "snow":
                weatherIcon = getDrawable(R.drawable.icon_14);
                break;
            case "sleet":
                weatherIcon = getDrawable(R.drawable.icon_5);
                break;
            case "wind":
                weatherIcon = getDrawable(R.drawable.icon_23);
                break;
            case "fog":
                weatherIcon = getDrawable(R.drawable.icon_22);
                break;
            case "cloudy":
                weatherIcon = getDrawable(R.drawable.icon_26);
                break;
            case "partly-cloudy-day":
                weatherIcon = getDrawable(R.drawable.icon_30);
                break;
            case "partly-cloudy-night":
                weatherIcon = getDrawable(R.drawable.icon_29);
                break;
            case "hail":
                weatherIcon = getDrawable(R.drawable.icon_18);
                break;
            case "thunderstorm":
                weatherIcon = getDrawable(R.drawable.icon_35);
                break;
            case "tornado":
                weatherIcon = getDrawable(R.drawable.icon_43);
                break;
            default:
                weatherIcon = getDrawable(R.drawable.icon_44);
                break;
        }
        weatherIconImageView.setImageDrawable(weatherIcon);

    }
    //show exception message on failure
    @Override
    public void serviceFailure(Exception e) {
       // progress.hide();    //hide progress dialog on failure
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
