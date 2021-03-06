package com.example.tugasbesarandroid;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private final String TAG = this.getTag();
    private Button mLoginButton;
    private EditText mEmailValue;
    private EditText mPassValue;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private LoginListener parentActivity;


    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        parentActivity = (LoginListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginButton = (Button) view.findViewById(R.id.loginButton);
        mEmailValue = (EditText) view.findViewById(R.id.loginEmailValue);
        mPassValue = (EditText) view.findViewById(R.id.loginPasswordValue);
        mProgressBar = view.findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmailValueString = mEmailValue.getText().toString();
                String mPassValueString = mPassValue.getText().toString();

                loginAccount(mEmailValueString,mPassValueString);
            }
        });
        view.findViewById(R.id.registerNowText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"REGISTER CLICKED");
                parentActivity.onNoAccountAvailable();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void loginAccount(String email, String password){
        mLoginButton.setEnabled(false);
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Login success");
                            parentActivity.onLoginSuccess();
                        } else {
                            Log.d(TAG, "Login failed");
                            mLoginButton.setEnabled(true);
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getSimpleName(),"DESTROYED");
    }

    public interface LoginListener {
        void onLoginSuccess();
        void onNoAccountAvailable();
    }
}
