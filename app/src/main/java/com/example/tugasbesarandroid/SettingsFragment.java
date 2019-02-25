package com.example.tugasbesarandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {
    private Activity parentActivity;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(parentActivity);
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Boolean switchPref = sharedPref.getBoolean(SettingsActivity.value_theme, false);
        String color = sharedPref.getString(SettingsActivity.value_theme_color, "O");
        if(switchPref){
            if(color.equals("O")) {
                parentActivity.setTheme(R.style.AppTheme2);
            }
            if(color.equals("G")) {
                parentActivity.setTheme(R.style.AppTheme4);
            }
            if(color.equals("B")) {
                parentActivity.setTheme(R.style.AppTheme6);
            }
            if(color.equals("R")) {
                parentActivity.setTheme(R.style.AppTheme8);
            }
        }
        else{
            if(color.equals("O")) {
                parentActivity.setTheme(R.style.AppTheme1);
            }
            if(color.equals("G")) {
                parentActivity.setTheme(R.style.AppTheme3);
            }
            if(color.equals("B")) {
                parentActivity.setTheme(R.style.AppTheme5);
            }
            if(color.equals("R")) {
                parentActivity.setTheme(R.style.AppTheme7);
            }
        }
        Preference prefscreen = ((Preference) findPreference("theme_color"));
        String valPref = sharedPref.getString("theme_color", "O");
        if(valPref.equals("O")){
            String backupPref = "Now the theme is orange";
            prefscreen.setSummary(backupPref);
        }
        else if(valPref.equals("G")){
            String backupPref = "Now the theme is gray";
            prefscreen.setSummary(backupPref);
        }
        else if(valPref.equals("B")){
            String backupPref = "Now the theme is blue";
            prefscreen.setSummary(backupPref);
        }
        else{
            String backupPref = "Now the theme is red";
            prefscreen.setSummary(backupPref);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (Activity) context;
    }
}
