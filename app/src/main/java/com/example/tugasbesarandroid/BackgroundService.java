package com.example.tugasbesarandroid;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class BackgroundService extends Service {

        public Context context = this;
        public Handler handler = null;
        public static Runnable runnable = null;

        @Override
        public void onCreate() {
            Toast.makeText(context, "Begin counting. Happy exercise!", Toast.LENGTH_SHORT).show();

            handler = new Handler();
            runnable = new Runnable() {
                public void run() {
                    Log.d("T","1");
                    Log.d("T","2");
                }
            };
        }

        @Override
        public void onDestroy() {
            /* IF YOU WANT THIS SERVICE KILLED WITH THE APP THEN UNCOMMENT THE FOLLOWING LINE */
            handler.removeCallbacks(runnable);
            Toast.makeText(this, "Stop counting. Did you walk/run enough?", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStart(Intent intent, int startid) {
            Toast.makeText(this, "Service started by user.", Toast.LENGTH_LONG).show();
        }

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
}
