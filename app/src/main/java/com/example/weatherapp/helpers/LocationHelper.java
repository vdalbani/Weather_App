package com.example.weatherapp.helpers;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

/**
 * WeatherApp created by vitto
 * on 2021-09-18
 */
public class LocationHelper {

    private final String TAG = this.getClass().getCanonicalName();
    public boolean locationPermissionGranted = false;
    public final int REQUEST_CODE_LOCATION = 101;
    private LocationRequest locationRequest;
    //Declare the fused location provider
    private FusedLocationProviderClient fusedLocationProviderClient = null;
    //declare location holder
    MutableLiveData<Location> mLocation = new MutableLiveData<>();

    private static final LocationHelper singletonInstance = new LocationHelper();

    public static LocationHelper getInstance(){
        return singletonInstance;
    }

    private LocationHelper(){
        this.locationRequest = new LocationRequest();
        //Set accuracy
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //Set Interval
        this.locationRequest.setInterval(20000); //20 seconds
    }

    public void checkPermission(Context context){
        this.locationPermissionGranted = (PackageManager.PERMISSION_GRANTED ==
                (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)));
        Log.d(TAG, "checkPermission: LOCATION PERMISSION GRANTED" + this.locationPermissionGranted);

        if(!this.locationPermissionGranted){
            requestLocationPermission(context);
        }
    }

    public void requestLocationPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                this.REQUEST_CODE_LOCATION);
    }

    public FusedLocationProviderClient getFusedLocationProviderClient(Context context) {
        if(this.fusedLocationProviderClient == null){
            this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        }
        return this.fusedLocationProviderClient;
    }

    //Create function to obtain last location
    @SuppressLint("MissingPermission")
    public MutableLiveData<Location> getLastLocation(Context context){
        if(this.locationPermissionGranted){
            try{
                this.getFusedLocationProviderClient(context)
                        .getLastLocation()
                        .addOnSuccessListener(location -> {
                            if(location != null){
                                mLocation.setValue(location);
                                Log.d(TAG, "onSuccess: LAST LOCATION OBTAINED: " + mLocation.getValue().getLatitude() + " " + mLocation.getValue().getLongitude());
                            }
                        })
                        .addOnFailureListener(e -> Log.e(TAG, "onFailure: EXCEPTION WHILE ACCESSING LAST LOCATION KNOWN " + e.getLocalizedMessage()));
            }catch (Exception e){
                Log.e(TAG, "getLastLocation: EXCEPTION ACCESSING LAST LOCATION " + e.getLocalizedMessage() );
                return null;
            }

            return this.mLocation;

        }else{
            Log.e(TAG, "getLastLocation: CERTAIN FEATURES NOT AVAILABLE WHEN NOT ACCES TO LOCATION");
            return null;
        }
    }

    //update the location
    @SuppressLint("MissingPermission")
    public void requestLocationUpdates(Context context, LocationCallback locationCallback){
        if(this.locationPermissionGranted){
            try{
                this.getFusedLocationProviderClient(context)
                        .requestLocationUpdates(this.locationRequest, locationCallback, Looper.getMainLooper());
            }catch(Exception e){
                Log.e(TAG, "requestLocationUpdates: " + e.getLocalizedMessage() );
            }
        }
    }

    //stop the location updates
    public void stopLocationUpdates(Context context, LocationCallback locationCallback){
        try{
            this.getFusedLocationProviderClient(context).removeLocationUpdates(locationCallback);
        }catch(Exception e){
            Log.e(TAG, "stopLocationUpdates: " + e.getLocalizedMessage() );
        }
    }

    //Forward Geocoding
    public Address performForwardGeocoding(Context context, Location loc){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try{
            addressList = geocoder.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);

            if(addressList.size() > 0){
                Address addressObj = addressList.get(0);
//                Log.d(TAG, "performForwardGeocoding: THE ADDRESS OBTAINED IS: " + addressObj.getAddressLine(0));
                return addressObj;
            }
        }catch (Exception e){
            Log.e(TAG, "performForwardGeocoding: " + e.getLocalizedMessage() );
        }

        return null;
    }


    public Address performForwardGeocoding(Context context, String cityName){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addressList;

        try{
            addressList = geocoder.getFromLocationName(cityName, 1);
            if(addressList.size() > 0){
                Address addressObj = addressList.get(0);
                Log.d(TAG, "performForwardGeocoding: THE ADDRESS OBTAINED IS: " + addressObj.getAddressLine(0));
                return addressObj;
            }
        }catch (Exception e){
            Log.e(TAG, "performForwardGeocoding: " + e.getLocalizedMessage() );
        }

        return null;
    }
}
