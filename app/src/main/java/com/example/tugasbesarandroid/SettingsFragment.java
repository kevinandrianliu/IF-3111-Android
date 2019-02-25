package com.example.tugasbesarandroid;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

public class SettingsFragment extends PreferenceFragmentCompat {
    private Activity parentActivity;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(parentActivity);
        setPreferencesFromResource(R.xml.preferences, rootKey);
        Boolean switchPref = sharedPref.getBoolean(SettingsActivity.value_theme, false);
        if(switchPref){
            parentActivity.setTheme(R.style.AppTheme2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (Activity) context;
    }
}
