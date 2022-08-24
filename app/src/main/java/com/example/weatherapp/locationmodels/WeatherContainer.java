package com.example.weatherapp.locationmodels;

import com.google.gson.annotations.SerializedName;

/**
 * WeatherApp created by vitto
 * on 2021-09-18
 */
public class WeatherContainer {
    private Current current;
    private @SerializedName("location") City currentCity;

    //Created to verify that location was correct
    public City getCurrentCity() {
        return currentCity;
    }

    public Current getCurrentWeather() {
        return current;
    }

    @Override
    public String toString() {
        return "WeatherContainer{" +
                "current=" + current +
                ", currentCity=" + currentCity +
                '}';
    }
}
