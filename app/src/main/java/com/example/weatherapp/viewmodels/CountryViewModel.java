package com.example.weatherapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.weatherapp.database.Country;
import com.example.weatherapp.repository.CountryRepository;

import java.util.List;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public class CountryViewModel extends AndroidViewModel {
    private CountryRepository countryRepository;
    private LiveData<List<Country>> allFavCountries;

    public CountryViewModel(Application application) {
        super(application);
        this.countryRepository = new CountryRepository(application);
        this.allFavCountries = this.countryRepository.allFavCountries;
    }

    public LiveData<List<Country>> getAllFavCountries(){
        return allFavCountries;
    }

    public void insertCountry(Country newCountry){
        this.countryRepository.insertCountry(newCountry);
    }

    public void deleteCountry(Country countryToDelete){
        this.countryRepository.deleteCountry(countryToDelete);
    }
}
