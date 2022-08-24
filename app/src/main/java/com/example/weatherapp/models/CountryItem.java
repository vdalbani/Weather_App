package com.example.weatherapp.models;

import com.google.gson.annotations.SerializedName;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */

public class CountryItem {

    private @SerializedName("alpha2Code") String countryCode;
    private @SerializedName("name") String name;
    private @SerializedName("capital") String capital;
    private @SerializedName("flag") String urlFlag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getUrlFlag() {
        return urlFlag;
    }

    public void setUrlFlag(String urlFlag) {
        this.urlFlag = urlFlag;
    }

    @Override
    public String toString() {
        return "CountryItem{" +
                "countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", urlFlag='" + urlFlag + '\'' +
                '}';
    }
}
