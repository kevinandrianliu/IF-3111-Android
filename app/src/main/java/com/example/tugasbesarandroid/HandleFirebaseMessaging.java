package com.example.tugasbesarandroid;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class HandleFirebaseMessaging extends FirebaseMessagingService {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.d(TAG, remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "Message notif body: " + remoteMessage.getNotification().getBody());
        }
    }
}
