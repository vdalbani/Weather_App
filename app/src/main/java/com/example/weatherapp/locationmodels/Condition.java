package com.example.weatherapp.locationmodels;

import com.google.gson.annotations.SerializedName;

/**
 * WeatherApp created by vitto
 * on 2021-09-18
 */
public class Condition {
    private @SerializedName("text") String weatherCondition;
    private String icon;

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "weatherCondition='" + weatherCondition + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
