package com.example.tugasbesarandroid;


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

    public CountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_count, container, false);

        stepsValue = view.findViewById(R.id.steps_value);
        distanceValue = view.findViewById(R.id.distance_value);
        startCounter = view.findViewById(R.id.start_counter);
        stopCounter = view.findViewById(R.id.stop_counter);

        startCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startCounter.isEnabled()){

                }
            }
        });
        stopCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stopCounter.isEnabled()){
                    //Stop counter
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}
