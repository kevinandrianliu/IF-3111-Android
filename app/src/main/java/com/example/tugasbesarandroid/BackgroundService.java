package com.example.tugasbesarandroid;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class BackgroundService extends Service implements LocationListener {
    private final String TAG = this.getClass().getSimpleName();
    private LocationManager locationManager;
    private Location location;

    private boolean isLocationAvail = false;
    private double latitude;
    private double longitude;

        public Handler handler = null;
        public static Runnable runnable = null;

        @Override
        public void onCreate() {
            Toast.makeText(this, "Begin counting. Happy exercise!", Toast.LENGTH_SHORT).show();



            handler = new Handler();
            runnable = new Runnable() {
                public void run() {
                    Location newLocation = getLocation();
                    float distance = 2.22f;

                    if (location == null){
                        location = newLocation;
                    } else {
                        distance = newLocation.distanceTo(location);
                        location = newLocation;
                    }

                    Intent intent = new Intent("GOGOGO");
                    intent.putExtra("distance",distance);
                    sendBroadcast(intent);
                    handler.postDelayed(runnable,10000);
                }
            };

            handler.postDelayed(runnable,10000);
        }

        @Override
        public void onDestroy() {
            /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
            stopLocation();
            handler.removeCallbacks(runnable);
            Toast.makeText(this, "Stop counting. Did you walk/run enough?", Toast.LENGTH_SHORT).show();
        }

        /**** LOCATION SERVICE ****/
        @SuppressLint("MissingPermission")
        public Location getLocation() {
            try {
                locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled){
                    Log.w(TAG,"No providers");
                    Toast.makeText(this,"No providers",Toast.LENGTH_SHORT).show();
                } else {
                    isLocationAvail = true;

                    if (isNetworkEnabled){
                        Toast.makeText(this,"Using Network",Toast.LENGTH_SHORT).show();
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 1, this);

                        if (locationManager != null){
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                            if (location != null){
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    } else if (isGPSEnabled){
                        Toast.makeText(this,"Using GPS",Toast.LENGTH_SHORT).show();
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, this);

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
        Toast.makeText(this, "Status changed: " + location.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this, "Status changed: " + status, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Provider enabled: " + provider, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Provider disabled: " + provider, Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
