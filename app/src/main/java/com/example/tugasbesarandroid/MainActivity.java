package com.example.tugasbesarandroid;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.squareup.picasso.Picasso;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;
/*
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

import static java.lang.Math.*;
*/

public class MainActivity extends AppCompatActivity {
    private double stepThreshold = 10.5;
    private final String TAG = this.getClass().getSimpleName();
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;

    private TextView gyroscopeValue;
    private TextView accelerometerValue;
    private TextView stepCount;
    private EditText thresholdValue;

    private long timeBefore;
    private int steps;

    private TextView mEmail;
    private TextView mEmailNavbar;
    private TextView mUid;

    private TextView mDisplayName;
    private ImageView mImage;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigation;
    private Intent mService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        FirebaseMessaging.getInstance().subscribeToTopic("quotes");
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sharedPref.getBoolean(SettingsActivity.value_theme, false);
        String color = sharedPref.getString(SettingsActivity.value_theme_color, "O");
        if(switchPref){
            if(color.equals("O")) {
                this.setTheme(R.style.AppTheme2);
            }
            if(color.equals("G")) {
                this.setTheme(R.style.AppTheme4);
            }
            if(color.equals("B")) {
                this.setTheme(R.style.AppTheme6);
            }
            if(color.equals("R")) {
                this.setTheme(R.style.AppTheme8);
            }
        }
        else{
            if(color.equals("O")) {
                this.setTheme(R.style.AppTheme1);
            }
            if(color.equals("G")) {
                this.setTheme(R.style.AppTheme3);
            }
            if(color.equals("B")) {
                this.setTheme(R.style.AppTheme5);
            }
            if(color.equals("R")) {
                this.setTheme(R.style.AppTheme7);
            }
        }
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout)findViewById(R.id.activity_main);
        mToggle = new ActionBarDrawerToggle(this, mDrawer,R.string.drawer_open, R.string.drawer_close);

        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigation = (NavigationView)findViewById(R.id.navigation_view);
        View mNavView = mNavigation.getHeaderView(0);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            mEmailNavbar = (TextView) mNavView.findViewById(R.id.email);
            mDisplayName = (TextView) mNavView.findViewById(R.id.display_name);
            mImage = (ImageView) mNavView.findViewById(R.id.image);

            mEmailNavbar.setText(user.getEmail());
            mDisplayName.setText(user.getDisplayName());
            Picasso.with(this).load(user.getPhotoUrl()).placeholder(R.drawable.icon).into(mImage);

            mService = new Intent(this,HandleFirebaseMessaging.class);

        } else {
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fragment_container, new CountFragment());
        fragmentTransaction.commit();

        mNavigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), EditProfileActivity.class));
                        break;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void editProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    public void subscribe(View view){
        FirebaseMessaging.getInstance().subscribeToTopic("daily_quotes")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String message = "Task successful";
                        if (!task.isSuccessful()){
                            message = "Task not successful";
                        }
                        Log.d("MainActivity", message);
                    }
                });
    }

    public void unsubscribe(View view){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("daily_quotes")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String message = "Task successful";
                        if (!task.isSuccessful()){
                            message = "Task not successful";
                        }
                        Log.d("MainActivity", message);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getSimpleName(),"MainActivity destroyed");
/*

        SensorManager sensorManager;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscopeSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        gyroscopeValue = (TextView) findViewById(R.id.gyroscope_value);
        accelerometerValue = (TextView) findViewById(R.id.accelerometer_value);
        stepCount = (TextView) findViewById(R.id.step_count);
        thresholdValue = (EditText) findViewById(R.id.threshold_value);

        steps = 0;

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                    double q0 = event.values[3];
                    double q1 = event.values[0];
                    double q2 = event.values[1];
                    double q3 = event.values[2];

                    double x = atan2(2.0 * (q0 * q1 + q2 * q3), 1.0 - 2.0 * (q1 * q1 + q2 * q2));
                    double y = asin(2.0 * (q0 * q2 - q3 * q1));
                    double z = atan2(2.0 * (q0 * q3 + q1 * q2), 1.0 - 2.0 * (q2 * q2 + q3 * q3));

                    x = x * 180 / PI;
                    y = y * 180 / PI;
                    z = z * 180 / PI;

                    String text =
                            "X = " + ((Double) x).toString() + "\n" +
                            "Y = " + ((Double) y).toString() + "\n" +
                            "Z = " + ((Double) z).toString() + "\n";;

                    gyroscopeValue.setText(text);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, gyroscopeSensor, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                long timeNow = event.timestamp;

                if (timeBefore == 0){
                    timeBefore = timeNow;
                } else {
                    if (timeNow - timeBefore > 100000000L) {
                        timeBefore = timeNow;
                        String text =
                                "X = " + ((Float) event.values[0]).toString() + "\n" +
                                        "Y = " + ((Float) event.values[1]).toString() + "\n" +
                                        "Z = " + ((Float) event.values[2]).toString() + "\n";

                        accelerometerValue.setText(text);

                        double magnitude = sqrt(event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2]);

                        if (magnitude > stepThreshold){
                            steps++;
                        }

                        stepCount.setText(String.format(Locale.getDefault(),"%d",steps/2));
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, accelerometerSensor, SensorManager.SENSOR_DELAY_UI);
    }

    public void setThreshold(View view){
        stepThreshold = Integer.decode(thresholdValue.getText().toString());
        steps = 0;
    }

    public void resetStep(View view) {
        steps = 0;
*/
    }
}
