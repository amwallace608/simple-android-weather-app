package com.example.myweatherapp.data;

import org.json.JSONObject;

//Interface that populates JSON object data
public interface JSONPopulator {
    void populate(JSONObject data);
}
