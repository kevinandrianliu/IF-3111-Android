package com.example.tugasbesarandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView mEmail;
    private TextView mUid;
    private TextView mDisplayName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            mEmail = (TextView) findViewById(R.id.main_email);
            mUid = (TextView) findViewById(R.id.main_uid);
            mDisplayName = (TextView) findViewById(R.id.display_name);

            mEmail.setText(user.getEmail());
            mUid.setText(user.getUid());
            mDisplayName.setText(user.getDisplayName());

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

    public void editProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getSimpleName(),"MainActivity destroyed");
    }
}
