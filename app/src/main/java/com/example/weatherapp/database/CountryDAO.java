package com.example.weatherapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
@Dao
public interface CountryDAO {
    @Query("SELECT * FROM tbl_favorite_countries ORDER BY ctr_name")
    LiveData<List<Country>> getFavoriteCountries();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Country country);

    @Delete
    void delete(Country country);
}
