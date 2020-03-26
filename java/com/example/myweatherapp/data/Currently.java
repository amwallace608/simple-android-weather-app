package com.example.myweatherapp.data;

import org.json.JSONObject;

public class Currently implements JSONPopulator {
    private int temperature;
    private String summary;
    private String icon;

    @Override
    public void populate(JSONObject data){  //get desired current weather data from JSON object
        summary = data.optString("summary");
        icon = data.optString("icon");
        temperature = data.optInt("temperature");
    }

    public int getTemperature() {
        return temperature;
    }

    public String getSummary() {
        return summary;
    }

    public String getIcon() {
        return icon;
    }
}