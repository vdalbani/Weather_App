package com.example.weatherapp.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.example.weatherapp.models.User;
import com.example.weatherapp.repository.UserRepository;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public class UserViewModel  extends AndroidViewModel {
    private final String TAG = this.getClass().getCanonicalName();
    private static UserViewModel myInstance;
    private final UserRepository userRepo = new UserRepository();

    public static UserViewModel getInstance(Application application){
        if(myInstance == null){
            myInstance = new UserViewModel(application);
        }
        return myInstance;
    }

    public UserRepository getUserRepo(){
        return userRepo;
    }

    private UserViewModel(Application application){
        super(application);
    }

    public void registerUser(User newUser) {this.userRepo.registerUser(newUser); }

    public void addUser(User newUser){
        this.userRepo.addUser(newUser);
    }

}
