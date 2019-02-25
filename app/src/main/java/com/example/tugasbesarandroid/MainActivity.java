package com.example.tugasbesarandroid;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView latitudeValue;
    private TextView longitudeValue;
    private TextView differenceValue;
    private Location lastKnownLocation;

    private LocationMonitor locationMonitor;

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeValue = findViewById(R.id.latitude_value);
        longitudeValue = findViewById(R.id.longitude_value);
        differenceValue = findViewById(R.id.difference_value);

        locationMonitor = new LocationMonitor(this);
        lastKnownLocation = null;
    }

    public void startLocation(View view) {
        if (locationMonitor.isLocationAvail()){
            Location currentLocation = locationMonitor.getLocation();

            if (lastKnownLocation == null){
                differenceValue.setText("0");
            } else {
                differenceValue.setText(String.format(Locale.getDefault(),"%f",lastKnownLocation.distanceTo(currentLocation)));
            }

            latitudeValue.setText(String.format(Locale.getDefault(),"%f",currentLocation.getLatitude()));
            longitudeValue.setText(String.format(Locale.getDefault(),"%f",currentLocation.getLongitude()));

            lastKnownLocation = currentLocation;
        }
    }

    public void stopLocation(View view){
        locationMonitor.stopLocation();
    }
}
