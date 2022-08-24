package com.example.weatherapp.network;

import com.example.weatherapp.models.CountryItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public interface API {
    //https://restcountries.eu/rest/v2/all
    //https://restcountries.eu/rest/v2/region/{region}
    String BASE_URL = "https://restcountries.eu/rest/v2/";

    //Get all countries
//    @GET("./all")
    @GET("https://restcountries.eu/rest/v2/all")
    Call<ArrayList<CountryItem>> retrieveAllCountries();

    //Get by Region
    //Easy to organize
    @GET("region/{region}")
    Call<ArrayList<CountryItem>> retrieveCountriesByRegion(@Path("region") String region);
}
