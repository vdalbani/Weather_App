package com.example.weatherapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */

@Entity(tableName = "tbl_favorite_countries")
public class Country {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "ctr_code")
    private String countryCode;

    @NonNull
    @ColumnInfo(name = "ctr_name")
    private String name;

    @ColumnInfo(name = "ctr_capital")
    private String capital;

    @ColumnInfo(name = "url_flag")
    private String urlFlag;

    public Country() {
    }

    public Country(String countryCode, @NonNull String name, String capital, String urlFlag) {
        this.countryCode = countryCode;
        this.name = name;
        this.capital = capital;
        this.urlFlag = urlFlag;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
        return "Country{" +
                "countryCode='" + countryCode + '\'' +
                ", name='" + name + '\'' +
                ", capital='" + capital + '\'' +
                ", urlFlag='" + urlFlag + '\'' +
                '}';
    }
}