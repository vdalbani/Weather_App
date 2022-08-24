package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.weatherapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    private final String TAG = this.getClass().getCanonicalName();
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        this.binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp();
            }
        });

        this.binding.btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogIn();
            }
        });
    }

    public void goToSignUp(){
        Intent SignUpIntent = new Intent(this, SignUpActivity.class);
        startActivity(SignUpIntent);
    }

    public void goToLogIn(){
        Intent LogInIntent = new Intent(this, LogInActivity.class);
        startActivity(LogInIntent);
    }
}