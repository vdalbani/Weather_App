package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.weatherapp.databinding.ActivityMainBinding;
import com.example.weatherapp.databinding.ActivitySignUpBinding;
import com.example.weatherapp.models.User;
import com.example.weatherapp.viewmodels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TAG = this.getClass().getCanonicalName();
    private ActivitySignUpBinding binding;
    private User newUser;
    private UserViewModel userViewModel;
    private Intent WelcomeIntent;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //initializing
        this.mAuth = FirebaseAuth.getInstance();
        this.userViewModel = UserViewModel.getInstance(this.getApplication());
        this.newUser = new User();

        WelcomeIntent = new Intent(this, WelcomeActivity.class);

        //set functionality
        this.binding.editBirthdate.setOnClickListener(this);
        this.binding.btnSignMeUp.setOnClickListener(this);
        this.binding.editBirthdate.setFocusable(false);

    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()){
                case R.id.btn_sign_me_up: {
                    if(this.validateData()){
                        registerUser();
                        break;
                    }
                    break;

                }
                case R.id.edit_birthdate:{
                    this.chooseDate();
                    break;
                }
            }
        }
    }


    private Boolean validateData(){
        Boolean result = true;

        //Variables
        String name = this.binding.editName.getText().toString();
        String phone = this.binding.editPhone.getText().toString();
        String address = this.binding.editAddress.getText().toString();
        String email = this.binding.editEmail.getText().toString();
        String password = this.binding.editPassword.getText().toString();

        if(name.isEmpty()){
            binding.editName.setError("Please enter name");
            binding.editName.setFocusable(true);
            return false;
        }

        if(phone.isEmpty()){
            binding.editPhone.setError("Please enter phone number");
            binding.editPhone.setFocusable(true);
            return false;
        }

        if(address.isEmpty()){
            binding.editAddress.setError("Please enter address");
            binding.editAddress.setFocusable(true);
            return false;
        }

        if(email.isEmpty()){
            binding.editEmail.setError("Please enter email");
            binding.editEmail.setFocusable(true);
            return false;
        }

        //If Email not formated correctly
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.editEmail.getText().toString()).matches()){
            binding.editEmail.setError("Please provide a valid email");
            return false;
        }

        //password validation
        if(password.isEmpty()){
            binding.editPassword.setError("Please provide a password");
            return false;
        }

        //Auth password must be at least 6 chars
        if(password.length() < 6){
            binding.editPassword.setError("Password must be at least 6 characters");
            return false;
        }

        return result;
    }

    private void chooseDate() {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        calendar.set(year, month, dayOfMonth);
                        newUser.setBirthDate(calendar.getTime());

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                        binding.editBirthdate.setText(sdf.format(newUser.getBirthDate()).toString()); // set the date

                    }
                }, year, month, day); // set date picker to current date

        datePicker.getDatePicker().setMaxDate(new Date().getTime());
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void registerUser(){

        String name = this.binding.editName.getText().toString();
        String phone = this.binding.editPhone.getText().toString();
        String address = this.binding.editAddress.getText().toString();
        String email = this.binding.editEmail.getText().toString();
        String password = this.binding.editPassword.getText().toString();

        newUser.setName(name);
        newUser.setPhoneNumber(phone);
        newUser.setAddress(address);
        newUser.setEmail(email);
        newUser.setPassword(phone);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //Adding a new User
                        userViewModel.registerUser(newUser);

                        //Confirm User
                        Toast.makeText(SignUpActivity.this,"USER SAVED SUCCESSFULLY", Toast.LENGTH_LONG).show();

                        //Redirect User
                        WelcomeIntent.putExtra("EMAIL_EXTRA", newUser.getEmail());
                        startActivity(WelcomeIntent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull  Exception e) {
                Toast.makeText(SignUpActivity.this,"EMAIL ALREADY EXISTS", Toast.LENGTH_LONG).show();
            }
        });
    }

    //USING ALTERNATIVE DATABASE IN FIRESTORE
    private void addNewUserToDB(){
        this.newUser.setName(this.binding.editName.getText().toString());
        this.newUser.setPhoneNumber(this.binding.editPhone.getText().toString());
        this.newUser.setAddress(this.binding.editAddress.getText().toString());
        this.newUser.setEmail(this.binding.editEmail.getText().toString());
        //date is assigned in the chooseDate()

        Log.d(TAG, "addNewUserToDB: USER: " + this.newUser.toString());

        //save to Database
//        this.userViewModel.addUser(newUser);

        //this.finish();

        //After successfully save
//        Intent WelcomeIntent = new Intent(this, WelcomeActivity.class);
//        startActivity(WelcomeIntent);

    }

}