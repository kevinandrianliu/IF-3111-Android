package com.example.tugasbesarandroid;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocationMonitor extends Service implements LocationListener {
    private final String TAG = this.getClass().getSimpleName();
    private LocationManager locationManager;
    private Location location;
    private Context context;

    private boolean isLocationAvail = false;
    private double latitude;
    private double longitude;


    LocationMonitor(Context context) {
        this.context = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled){
                Log.w(TAG,"No providers");
            } else {
                isLocationAvail = true;

                if (isNetworkEnabled){
                    Toast.makeText(context,"Using network",Toast.LENGTH_SHORT).show();
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 1, this);

                    if (locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                        if (location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                } else if (isGPSEnabled){
                    Toast.makeText(context,"Using GPS",Toast.LENGTH_SHORT).show();
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 1, this);

                    if (locationManager != null){
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if (location != null){
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public void stopLocation(){
        if (locationManager != null){
            locationManager.removeUpdates(this);
        }
    }

    public double getLatitude(){
        if (location != null){
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude(){
        if (location != null){
            longitude = location.getLongitude();
        }

        return longitude;
    }

    public boolean isLocationAvail(){
        return isLocationAvail;
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(context, "Location changed: " + location.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(context, "Status changed: " + status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(context, "Provider enabled: " + provider, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(context, "Provider disabled: " + provider, Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
