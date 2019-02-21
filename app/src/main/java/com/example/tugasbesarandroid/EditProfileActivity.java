package com.example.tugasbesarandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mDisplayNameValue;
    private Button mSaveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mDisplayNameValue = (EditText) findViewById(R.id.display_name_value);
        mSaveChanges = (Button) findViewById(R.id.save_changes);

        mSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mDisplayNameValueString = mDisplayNameValue.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mDisplayNameValueString)
                            .build();

                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(EditProfileActivity.class.getSimpleName(), "User profile updated.");
                                        Toast.makeText(EditProfileActivity.this, "User profile updated", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                    }
                    else {
                    Intent intent = new Intent(EditProfileActivity.this, LoginRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(EditProfileActivity.class.getSimpleName(),"EditProfileActivity destroyed");
    }


}
