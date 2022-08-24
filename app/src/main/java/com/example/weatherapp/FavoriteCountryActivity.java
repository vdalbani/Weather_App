package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.weatherapp.adapters.CountryAdapter;
import com.example.weatherapp.adapters.OnCountryItemClickListener;
import com.example.weatherapp.database.Country;
import com.example.weatherapp.databinding.ActivityFavoriteCountryBinding;
import com.example.weatherapp.models.CountryItem;
import com.example.weatherapp.viewmodels.CountryViewModel;

import java.util.ArrayList;
import java.util.List;


public class FavoriteCountryActivity extends AppCompatActivity implements OnCountryItemClickListener {
    private ActivityFavoriteCountryBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<CountryItem> favoriteList;
    private CountryAdapter countryAdapter;
    private CountryViewModel countryViewModel;
    private CountryItem tempCountryItem;
    private ItemTouchHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityFavoriteCountryBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        this.countryViewModel = new ViewModelProvider(this).get(CountryViewModel.class);

        this.favoriteList = new ArrayList<>();
        this.countryAdapter = new CountryAdapter(this,this.favoriteList,this);
        this.binding.rvFavoriteList.setAdapter(countryAdapter);
        this.binding.rvFavoriteList.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvFavoriteList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        //Initiate helper (Delete left swipe)
        this.helper = new ItemTouchHelper(simpleCallback);
        this.helper.attachToRecyclerView(this.binding.rvFavoriteList);

        //Retrieve favorite meals from db
        this.countryViewModel.getAllFavCountries().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> favCountries) {
                if(!favCountries.isEmpty()){
                    Log.d(TAG, "onChanged: COUNTRIES RECEIVED" + favCountries.toString());
                    favoriteList.clear();

                    for(Country tempFavCountry : favCountries){
                        tempCountryItem = new CountryItem();
                        tempCountryItem.setCountryCode(tempFavCountry.getCountryCode());
                        tempCountryItem.setName(tempFavCountry.getName());
                        tempCountryItem.setCapital(tempFavCountry.getCapital());
                        tempCountryItem.setUrlFlag(tempFavCountry.getUrlFlag());

                        favoriteList.add(tempCountryItem);
                    }
                    countryAdapter.notifyDataSetChanged();
                }else{
                    Log.e(TAG, "onChanged: EMPTY LIST");
                }
            }
        });
    }

    @Override
    public void onCountryItemClicked(CountryItem countryItem) {

//        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
//        deleteAlert.setTitle("Remove from Favorites");
//        deleteAlert.setMessage("Do you want to remove "+ countryItem.getName() +" from your Favorites?");
//
//        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                deleteFavorite(countryItem);
//            }
//        });
//
//        deleteAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//
//        deleteAlert.show();


        //Add Redirection to Weather
        Intent WeatherActivity = new Intent(this, WeatherActivity.class);
        //TODO: CAN SEND COUNTRY CODE
        WeatherActivity.putExtra("EXTRA_COUNTRY", countryItem.getName());
        WeatherActivity.putExtra("EXTRA_CAPITAL", countryItem.getCapital());
        Log.d(TAG, "onCountryItemClicked: " + countryItem.getCapital());
        startActivity(WeatherActivity);

    }

    //    Delete the Item selected swiping to the left
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction == ItemTouchHelper.LEFT){
                //delete from Database
                deleteFavorite(viewHolder.getAdapterPosition());
            }
        }
    };

    private void deleteFavorite1(CountryItem countryItem){
        Country tempToDelete = new Country();
        tempToDelete.setCountryCode(countryItem.getCountryCode());
        tempToDelete.setName(countryItem.getName());
        tempToDelete.setCapital(countryItem.getCapital());
        tempToDelete.setUrlFlag(countryItem.getUrlFlag());

        //Delete
        countryViewModel.deleteCountry(tempToDelete);

        Toast.makeText(getApplicationContext(),
                tempToDelete.getName() + " has been removed",
                Toast.LENGTH_LONG).show();
    }

    private void deleteFavorite(int position){
        CountryItem selectedCountry =  favoriteList.get(position);

        AlertDialog.Builder deleteAlert = new AlertDialog.Builder(this);
        deleteAlert.setTitle("Remove from Favorites");
        deleteAlert.setMessage("Do you want to remove this country from your list?");

        deleteAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //delete from database
                Country coffeeOrderToDelete = new Country();
                coffeeOrderToDelete.setCountryCode(selectedCountry.getCountryCode());
                coffeeOrderToDelete.setName(selectedCountry.getName());
                coffeeOrderToDelete.setCapital(selectedCountry.getCapital());
                coffeeOrderToDelete.setUrlFlag(selectedCountry.getUrlFlag());

                countryViewModel.deleteCountry(coffeeOrderToDelete);

                Toast.makeText(getApplicationContext(),
                        "Order has been removed from the list",
                        Toast.LENGTH_LONG).show();
            }
        });

        deleteAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                countryAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        deleteAlert.show();

    }
}