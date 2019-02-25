package com.example.tugasbesarandroid;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CountFragment extends Fragment {
    private TextView stepsValue;
    private TextView distanceValue;
    private Button startCounter;
    private Button stopCounter;

    private Context context;
    private Intent backgroundServiceIntent;

    public CountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_count, container, false);

        stepsValue = view.findViewById(R.id.steps_value);
        distanceValue = view.findViewById(R.id.distance_value);
        startCounter = view.findViewById(R.id.start_counter);
        stopCounter = view.findViewById(R.id.stop_counter);

        backgroundServiceIntent = new Intent(context, BackgroundService.class);

        startCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startCounter.isEnabled()){
                    context.startService(backgroundServiceIntent);
                    startCounter.setEnabled(false);
                    stopCounter.setEnabled(true);
                }
            }
        });
        stopCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stopCounter.isEnabled()){
                    context.stopService(backgroundServiceIntent);
                    startCounter.setEnabled(true);
                    stopCounter.setEnabled(false);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
