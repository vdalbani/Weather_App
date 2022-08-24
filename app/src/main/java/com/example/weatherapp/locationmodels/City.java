package com.example.weatherapp.locationmodels;

import com.google.gson.annotations.SerializedName;

/**
 * WeatherApp created by vitto
 * on 2021-09-18
 */
public class City {
    private @SerializedName("name") String city;

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "City{" +
                "city='" + city + '\'' +
                '}';
    }
}
