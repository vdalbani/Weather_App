package com.example.weatherapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.weatherapp.database.AppDB;
import com.example.weatherapp.database.Country;
import com.example.weatherapp.database.CountryDAO;

import java.util.List;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public class CountryRepository {

    private AppDB db;
    private CountryDAO countryDAO;
    public LiveData<List<Country>> allFavCountries;

    public CountryRepository(Application application) {
        this.db = AppDB.getDatabase(application);
        this.countryDAO = this.db.countryDAO();
        this.allFavCountries = this.countryDAO.getFavoriteCountries();
    }

    //Add the favorite country
    public void insertCountry(Country newCountry){
        AppDB.databaseWriteExecutor.execute(()->{
            countryDAO.insert(newCountry);
        });
    }

    //retrieve all favorite countries
    public LiveData<List<Country>> getAllFavCountries(){
        return this.allFavCountries;
    }

    //delete a favorite country
    public void deleteCountry(Country countryToDelete){
        AppDB.databaseWriteExecutor.execute(()->{
            this.countryDAO.delete(countryToDelete);
        });
    }
}
