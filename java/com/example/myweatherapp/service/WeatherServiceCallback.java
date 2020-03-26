package com.example.myweatherapp.service;

import com.example.myweatherapp.data.Currently;

public interface WeatherServiceCallback {
    void serviceSuccess(Currently currently);
    void serviceFailure(Exception e);
}
