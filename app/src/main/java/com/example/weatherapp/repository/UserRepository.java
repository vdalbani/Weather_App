package com.example.weatherapp.repository;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.weatherapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

/**
 * WeatherApp created by vitto
 * on 2021-09-12
 */
public class UserRepository {

    private final String TAG = this.getClass().getCanonicalName();
    private final FirebaseFirestore db;
    private final String COLLECTION_NAME = "Users";
    private final FirebaseDatabase fbd;

    public UserRepository(){
        //Firebase Database
        this.db = FirebaseFirestore.getInstance();
        //Realtime Database
        this.fbd = FirebaseDatabase.getInstance();
    }

    //To Firebase Collection
    public void addUser(User user){
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("name", user.getName());
            data.put("phoneNumber", user.getPhoneNumber());
            data.put("birthDate", user.getBirthDate());
            data.put("address", user.getAddress());
            data.put("email", user.getEmail());
            data.put("password", user.getPassword());

            Log.d(TAG, "addUser: " + data.toString());

            this.db.collection(COLLECTION_NAME)
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "onSuccess: Document created with ID : " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: Document can't be created" + e.getLocalizedMessage() );
                        }
                    });

        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }

    public void registerUser(User user){
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("name", user.getName());
            data.put("phoneNumber", user.getPhoneNumber());
            data.put("birthDate", user.getBirthDate());
            data.put("address", user.getAddress());
            data.put("email", user.getEmail());
            data.put("password", user.getPassword());

            Log.d(TAG, "addUser: " + data.toString());

            this.fbd.getReference(COLLECTION_NAME)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .setValue(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull  Task<Void> task) {
                            Log.d(TAG, "onComplete: SUCCESSFULLY SAVED");
                        }
                    });
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
            Log.e(TAG, ex.getLocalizedMessage());
        }
    }
}

