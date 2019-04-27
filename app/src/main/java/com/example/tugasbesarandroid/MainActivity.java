package com.example.tugasbesarandroid;

import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements ComponentCallbacks2 {
    private static final double stepThreshold = 10.5;
    private final String TAG = this.getClass().getSimpleName();
    private Sensor accelerometerSensor;
    private Sensor gyroscopeSensor;

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
        fragmentTransaction.replace(R.id.main_fragment_container, new CountFragment());
        fragmentTransaction.addToBackStack(null);
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
                    case R.id.sign_out:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), LoginRegisterActivity.class));
                        finish();
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

    public void setThreshold(View view){
        stepThreshold = Integer.decode(thresholdValue.getText().toString());
        steps = 0;
    }

    public void resetStep(View view) {
        steps = 0;
*/
    }

}
