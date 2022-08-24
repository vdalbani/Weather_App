package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.weatherapp.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent WelcomeIntent;
    private FirebaseAuth mAuth;
    private ActivityLogInBinding binding;
    private final String TAG = this.getClass().getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Initialize
        mAuth = FirebaseAuth.getInstance();

        WelcomeIntent = new Intent(this, WelcomeActivity.class);

        this.binding.btnLogInB.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()){
                case R.id.btn_log_in_b: {
                    if(this.validateData()){
                        userLogIn();
                        break;
                    }
                    break;
                }
            }
        }
    }

    private boolean validateData() {
        Boolean result = true;

        //Variables
        String email = this.binding.editLoginEmail.getText().toString();
        String password = this.binding.editLoginPassword.getText().toString();

        if(email.isEmpty()){
            binding.editLoginEmail.setError("Please enter email");
            binding.editLoginEmail.setFocusable(true);
            return false;
        }

        //If Email not formated correctly
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.editLoginEmail.getText().toString()).matches()){
            binding.editLoginEmail.setError("Please provide a valid email");
            return false;
        }

        //password validation
        if(password.isEmpty()){
            binding.editLoginPassword.setError("Please provide a password");
            return false;
        }

        return result;
    }

    private void userLogIn() {
        String email = this.binding.editLoginEmail.getText().toString().trim();
        String password = this.binding.editLoginPassword.getText().toString().trim();

        Log.d(TAG, "userLogIn: "+email);
        Log.d(TAG, "userLogIn: " + password);

        mAuth.signInWithEmailAndPassword(this.binding.editLoginEmail.getText().toString().trim(),this.binding.editLoginPassword.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //redirect
                            Log.d(TAG, "onComplete: SUCCESSFULLY LOGGED IN");

                            startActivity(WelcomeIntent);
                            WelcomeIntent.putExtra("EMAIL_EXTRA", email);
                            Toast.makeText(LogInActivity.this, "Welcome Back", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(LogInActivity.this, "Failed to log in, please review your password and email", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}