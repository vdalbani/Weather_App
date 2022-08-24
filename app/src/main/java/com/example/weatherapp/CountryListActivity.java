package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.weatherapp.adapters.CountryAdapter;
import com.example.weatherapp.adapters.OnCountryItemClickListener;
import com.example.weatherapp.database.Country;
import com.example.weatherapp.databinding.ActivityCountryListBinding;
import com.example.weatherapp.models.CountryItem;
import com.example.weatherapp.network.RetrofitClient;
import com.example.weatherapp.viewmodels.CountryViewModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryListActivity extends AppCompatActivity implements OnCountryItemClickListener {
    private final String TAG = this.getClass().getCanonicalName();
    ActivityCountryListBinding binding;
    private ArrayList<CountryItem> countryItemArrayList;
    private String selectedRegion = "";
    private CountryAdapter countryAdapter;
    private CountryViewModel countryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityCountryListBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        //INITITATE THE VIEW
        this.countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);

        //INITIATE THE LIST
        this.countryItemArrayList = new ArrayList<>();
        countryAdapter = new CountryAdapter(this, countryItemArrayList,this);
        this.binding.rvCountryList.setAdapter(countryAdapter);
        this.binding.rvCountryList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvCountryList.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //Getting the Region to Display data
        this.selectedRegion = this.getIntent().getStringExtra("EXTRA_REGION");
        if (!this.selectedRegion.isEmpty()) {
            this.getCountryListByRegion();
        }
    }
    private void getCountryListByRegion(){
//            Call<ArrayList<CountryItem>> call = RetrofitClient.getInstance().getApi().retrieveAllCountries();
        Call<ArrayList<CountryItem>> countryCall = RetrofitClient.getInstance().getApi().retrieveCountriesByRegion(this.selectedRegion);

        try{
            countryCall.enqueue(new Callback<ArrayList<CountryItem>>() {
                @Override
                public void onResponse(Call<ArrayList<CountryItem>> call, Response<ArrayList<CountryItem>> response) {

                    if(response.code()== 200 && response.body() !=null){
                        //SET THE RECYCLER VIEW
                        ArrayList<CountryItem> obtained_response =  response.body();

                        Log.d(TAG, "onResponse: RESPONSE: OBJECTS OBTAINED " + obtained_response);
                        countryItemArrayList.clear();
                        countryItemArrayList.addAll(obtained_response);
                        countryAdapter.notifyDataSetChanged();

                    }else{
                        Log.d(TAG, "onResponse: NO RESPONSE RECEIVED");
                    }

                    //CANCEL THE CALL
                    countryCall.cancel();
                }

                @Override
                public void onFailure(Call<ArrayList<CountryItem>> call, Throwable t) {
                    Log.e(TAG, "ERROR WHILE FETCHING DATA! : " + t.getLocalizedMessage());
                }
            });
        }catch(Exception e){
            Log.e(TAG, "getCountryListByRegion: EXCEPTION OCCURRED: " + e.getLocalizedMessage());
        }
    }

    //CLICK ALLOW TO SAVE
    @Override
    public void onCountryItemClicked(CountryItem countryItem) {

        AlertDialog.Builder saveAlert = new AlertDialog.Builder(this);
        saveAlert.setTitle("Add to Favorites");
        saveAlert.setMessage("Do you want to add "+countryItem.getName() +" to your Favorites?");

        saveAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //SAVE FAVORITES WHEN CONFIRMED
                saveFavorite(countryItem);
            }
        });

        saveAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        saveAlert.show();
    }

    public void saveFavorite(CountryItem countryItem){

        Country newCountry = new Country();
        newCountry.setCountryCode(countryItem.getCountryCode());
        newCountry.setName(countryItem.getName());
        newCountry.setCapital(countryItem.getCapital());
        newCountry.setUrlFlag(countryItem.getUrlFlag());

        this.countryViewModel.insertCountry(newCountry);

        Log.d(TAG, "saveFavorite: COUNTRY SAVED: " + newCountry.getName());

        Toast.makeText(getApplicationContext(),
                newCountry.getName() + " has been saved",
                Toast.LENGTH_LONG).show();

    }

    //CREATE A MENU TO DISPLAY
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_show_fav:{
                Intent favIntent = new Intent(this, FavoriteCountryActivity.class);
                startActivity(favIntent);
                break;
            }
            case R.id.action_show_main:{
                Intent mainIntent = new Intent(this, MainActivity.class);
                startActivity(mainIntent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}