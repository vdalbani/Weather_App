package com.example.weatherapp.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
* WeatherApp created by vitto
* on 2021-09-18 */
public class WeatherRetrofitClient {
    private static WeatherRetrofitClient instance = null;
    private WeatherAPI api;

    private WeatherRetrofitClient(){
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(WeatherAPI.class);
    }

    //singleton design pattern
    public static synchronized WeatherRetrofitClient getInstance() {
        if (instance == null){
            instance = new WeatherRetrofitClient();
        }

        return instance;
    }

    public WeatherAPI getApi() {
        return api;
    }
}
