package com.example.tugasbesarandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    public static final String value_theme = "theme_option";
    public static final String value_theme_color = "theme_color";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }
}
