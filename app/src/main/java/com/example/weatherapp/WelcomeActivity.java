package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.example.weatherapp.databinding.ActivityWelcomeBinding;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityWelcomeBinding binding;
    private final String TAG = this.getClass().getCanonicalName();
    private ArrayList<String> regionsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());

        //CREATED A LIST AS API DOES NOT PROVIDE QUERY FOR ALL REGIONS
        this.regionsList = new ArrayList<>();
        regionsList.add("Select a Region");
        regionsList.add("Asia");
        regionsList.add("Oceania");
        regionsList.add("Europe");
        regionsList.add("Americas");
        regionsList.add("Africa");

        //Create the adapter for spinner
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, this.regionsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply adapter to the spinner
        this.binding.spnRegions.setAdapter(adapter);
        this.binding.spnRegions.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        if(regionsList.get(position) != "Select a Region"){
            Intent countryListIntent = new Intent(this, CountryListActivity.class);
            countryListIntent.putExtra("EXTRA_REGION", regionsList.get(position));
            startActivity(countryListIntent);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //DO NOTHING
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        }
        return super.onOptionsItemSelected(item);
    }
}