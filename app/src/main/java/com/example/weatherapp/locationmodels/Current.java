package com.example.weatherapp.locationmodels;

import com.google.gson.annotations.SerializedName;

/**
 * WeatherApp created by vitto
 * on 2021-09-18
 */
public class Current {
    private @SerializedName("temp_c") double temperature;
    private @SerializedName("wind_degree") int wind;
    private @SerializedName("wind_dir") String windDirection;
    private int humidity;
    private @SerializedName("feelslike_c") double feelsLike;
    private @SerializedName("vis_km") double visibility;
    private @SerializedName("uv") double uvIndex;
    private Condition condition;

    public double getTemperature() {
        return temperature;
    }

    public int getWind() {
        return wind;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getUvIndex() {
        return uvIndex;
    }

    public Condition getCondition() {
        return condition;
    }

    @Override
    public String toString() {
        return "Current{" +
                "temperature=" + temperature +
                ", wind=" + wind +
                ", windDirection='" + windDirection + '\'' +
                ", humidity=" + humidity +
                ", feelsLike=" + feelsLike +
                ", visibility=" + visibility +
                ", uvIndex=" + uvIndex +
                ", condition=" + condition +
                '}';
    }
}
