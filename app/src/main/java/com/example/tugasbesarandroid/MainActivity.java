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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mEmail;
    private TextView mEmailNavbar;
    private TextView mUid;
    private TextView mDisplayName;
    private ImageView mImage;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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

    public void editProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getSimpleName(),"MainActivity destroyed");
    }
}
