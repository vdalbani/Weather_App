package com.example.weatherapp.network;

import com.example.weatherapp.locationmodels.WeatherContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * WeatherApp created by vitto
 * on 2021-09-18
 */
public interface WeatherAPI {

        String BASE_URL = "https://api.weatherapi.com/v1/";

        @GET("./current.json?key={YOUR_KEY}")
        Call<WeatherContainer> retrieveCurrentWeatherByCoordinates(@Query("q") String coordinates);

        @GET("./current.json?key={YOUR_KEY}")
        Call<WeatherContainer> retrieveCurrentWeatherByCapital(@Query("q") String countryName);

}
