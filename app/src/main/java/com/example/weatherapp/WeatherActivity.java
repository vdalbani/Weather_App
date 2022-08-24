package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.weatherapp.databinding.ActivityWeatherBinding;
import com.example.weatherapp.locationmodels.Current;
import com.example.weatherapp.locationmodels.WeatherContainer;
import com.example.weatherapp.network.WeatherRetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getCanonicalName();
    private ActivityWeatherBinding binding;
    private String country;
    private String capital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        country = getIntent().getStringExtra("EXTRA_COUNTRY");
        capital = getIntent().getStringExtra("EXTRA_CAPITAL");

        //Initialize Method
        getCurrentWeather(country,capital);

    }

    private void getCurrentWeather(String countryName, String capital){

        Call<WeatherContainer> weatherContainerCall = WeatherRetrofitClient.getInstance()
                .getApi()
                .retrieveCurrentWeatherByCapital(capital);

        try{
            weatherContainerCall.enqueue(new Callback<WeatherContainer>() {
                @Override
                public void onResponse(Call<WeatherContainer> call, Response<WeatherContainer> response) {
                    if(response.code() == 200){
                        WeatherContainer weatherContainerResponse = response.body();
                        Log.d(TAG, "onResponse: " + weatherContainerResponse.toString());

                        if(weatherContainerResponse != null){
                            Current currentWeather = weatherContainerResponse.getCurrentWeather();

                            //PERFORM THE BINDING WITH THE LAYOUT
                            binding.tvLocationAddress.setText(countryName + " - " + capital);
                            binding.tvOvercast.setText(currentWeather.getCondition().getWeatherCondition());
                            binding.tvDispTemperature.setText(Double.toString(currentWeather.getTemperature()) + " C");
                            binding.tvDispFeelsLike.setText(Double.toString(currentWeather.getFeelsLike()) + " C");
                            binding.tvDispWind.setText(Integer.toString(currentWeather.getWind()));
                            binding.tvDispWindDirection.setText(currentWeather.getWindDirection());
                            binding.tvDispHumidity.setText(Double.toString(currentWeather.getHumidity()));
                            binding.tvDispUvIndex.setText(Double.toString(currentWeather.getUvIndex()));
                            binding.tvDispVisibility.setText(Double.toString(currentWeather.getVisibility()) + "km");

                            Glide.with(getApplicationContext())
                                    .load("https:" + currentWeather.getCondition().getIcon())
                                    .into(binding.imgIcon);

                        }else{
                            Log.e(TAG, "onResponse: WEATHER CONTAINER IS EMPTY");
                        }
                    }else{
                        Log.e(TAG, "onResponse: UNSUCCESSFUL RESPONSE" );
                    }

                    //Cancel the Call
                    weatherContainerCall.cancel();
                }

                @Override
                public void onFailure(Call<WeatherContainer> call, Throwable t) {
                    Log.e(TAG, "onFailure: ERROR WHILE FETCHING WEATHER IN THE ENQUEUE MAIN" );
                }
            });
        }catch(Exception e){
            Log.e(TAG, "getCurrentWeather: ERROR WHILE FETCHING DATA " + e.getLocalizedMessage());
        }
    }
}