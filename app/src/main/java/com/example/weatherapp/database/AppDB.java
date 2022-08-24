package com.example.weatherapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */

@Database(entities = {Country.class}, version =1,exportSchema = false)
public abstract class AppDB extends RoomDatabase {

    private static volatile AppDB db;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract CountryDAO countryDAO();

    public static AppDB getDatabase(final Context context){
        if (db == null){
            db = Room.databaseBuilder(context.getApplicationContext(), AppDB.class, "db_meal")
                    .fallbackToDestructiveMigration()
                    .build();

        }
        return db;
    }
}

