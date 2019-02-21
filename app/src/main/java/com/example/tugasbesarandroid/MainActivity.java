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
import com.google.firebase.auth.GetTokenResult;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private TextView mEmail;
    private TextView mUid;
    private Button mSendToken;
    private TextView mResponseText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        mSendToken = (Button) findViewById(R.id.send_token);
        mResponseText = (TextView) findViewById(R.id.response_text);

        if (user != null){
            mEmail = (TextView) findViewById(R.id.main_email);
            mUid = (TextView) findViewById(R.id.main_uid);

            mEmail.setText(user.getEmail());
            mUid.setText(user.getUid());
        } else {
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void sendToken(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.getIdToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    @Override
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()){
                            new SendToken(mResponseText).execute(task.getResult().getToken());
                        } else {
                            Log.w(this.getClass().getSimpleName(),"Can't get token");
                        }
                    }
                });
    }

    public void signOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(MainActivity.class.getSimpleName(),"MainActivity destroyed");
    }
}
