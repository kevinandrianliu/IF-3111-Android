package com.example.tugasbesarandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;

public class EditProfileActivity extends AppCompatActivity {

    private EditText mDisplayNameValue;
    private Button mSelectPic;
    private Button mSaveChanges;
    private ImageView mImage;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDisplayNameValue = (EditText) findViewById(R.id.display_name_value);
        mSaveChanges = (Button) findViewById(R.id.save_changes);
        mSelectPic = (Button) findViewById(R.id.upload_pic_button);
        mImage = (ImageView) findViewById(R.id.image);


        mSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mDisplayNameValueString = mDisplayNameValue.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {

                    if (mDisplayNameValueString.matches("")) {
                        Toast.makeText(getApplicationContext(), "Please enter a name", Toast.LENGTH_SHORT).show();

                    } else {

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(mDisplayNameValueString)
                                .setPhotoUri(filePath)
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
                                            finish();
                                        }
                                    }
                                });
                        }
                    }
                    else {
                    Intent intent = new Intent(EditProfileActivity.this, LoginRegisterActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        mSelectPic.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("scale", true);
                        intent.putExtra("outputX", 100);
                        intent.putExtra("outputY", 100);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, 1);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1) {
                filePath = data.getData();

            try
            {
                Bitmap newProfilePic = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                newProfilePic.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                mImage.setImageBitmap(newProfilePic);
            }
            catch (Exception e)
            {
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(EditProfileActivity.class.getSimpleName(),"EditProfileActivity destroyed");
    }


}
