package com.example.tugasbesarandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView mEmail;
    private TextView mUid;
    private Intent mService;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            mEmail = (TextView) findViewById(R.id.main_email);
            mUid = (TextView) findViewById(R.id.main_uid);

            mEmail.setText(user.getEmail());
            mUid.setText(user.getUid());

            mService = new Intent(this,HandleFirebaseMessaging.class);
        } else {
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public void subscribe(View view){
        FirebaseMessaging.getInstance().subscribeToTopic("daily_quotes")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String message = "Task successful";
                        if (!task.isSuccessful()){
                            message = "Task not successful";
                        }
                        Log.d("MainActivity", message);
                    }
                });
    }

    public void unsubscribe(View view){
        FirebaseMessaging.getInstance().unsubscribeFromTopic("daily_quotes")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String message = "Task successful";
                        if (!task.isSuccessful()){
                            message = "Task not successful";
                        }
                        Log.d("MainActivity", message);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getSimpleName(),"MainActivity destroyed");
    }
}
