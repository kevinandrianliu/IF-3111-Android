package com.example.tugasbesarandroid;

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

public class MainActivity extends AppCompatActivity {
    private double stepThreshold = 10.5;
    private final String TAG = this.getClass().getSimpleName();
    private Sensor accelerometerSensor;
    private Sensor linearAcceleration;

    private TextView lineaccValue;
    private TextView accelerometerValue;
    private TextView stepCount;
    private EditText thresholdValue;
    private TextView mMovementType;

    private long timeBefore;
    private int steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager;

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        linearAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        lineaccValue = (TextView) findViewById(R.id.accel_value);
        accelerometerValue = (TextView) findViewById(R.id.accelerometer_value);
        mMovementType = (TextView) findViewById(R.id.type);
        stepCount = (TextView) findViewById(R.id.step_count);
        thresholdValue = (EditText) findViewById(R.id.threshold_value);

        steps = 0;

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

                        double magnitude = sqrt(event.values[0]*event.values[0] + event.values[1]*event.values[1] + event.values[2]*event.values[2]);

                        lineaccValue.setText(text);


                        if (magnitude >= 10) {
                            mMovementType.setText("RUN");
                        } else {
                            mMovementType.setText("WALK");
                        }

                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, linearAcceleration, SensorManager.SENSOR_DELAY_UI);
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
    }
}
