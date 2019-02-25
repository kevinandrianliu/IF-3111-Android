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
    private Sensor gyroscopeSensor;

    private TextView gyroscopeValue;
    private TextView accelerometerValue;
    private TextView stepCount;
    private EditText thresholdValue;

    private long timeBefore;
    private int steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
    }
}
