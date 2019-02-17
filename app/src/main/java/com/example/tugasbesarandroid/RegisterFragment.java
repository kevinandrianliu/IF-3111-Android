package com.example.tugasbesarandroid;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {
    private final String TAG = this.getTag();
    private Button mRegisterButton;
    private EditText mEmailValue;
    private EditText mPasswordValue;
    private EditText mRepeatPasswordValue;
    private FirebaseAuth mAuth;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        mRegisterButton = view.findViewById(R.id.registerButton);
        mEmailValue = view.findViewById(R.id.registerEmailValue);
        mPasswordValue = view.findViewById(R.id.registerPasswordValue);
        mRepeatPasswordValue = view.findViewById(R.id.registerRetypePasswordValue);
        mAuth = FirebaseAuth.getInstance();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPasswordValue.equals(mRepeatPasswordValue)){
                    try {
                        registerAccount(mEmailValue.getText().toString(), mPasswordValue.getText().toString());
                    } catch (FirebaseAuthWeakPasswordException e){
                        Log.d(TAG,e.getReason());
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        Log.d(TAG,e.getMessage());
                    } catch (FirebaseAuthUserCollisionException e){
                        Log.d(TAG,e.getMessage());
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getSimpleName(), "DESTROYED");
    }

    private void registerAccount(String email, String password) throws
            FirebaseAuthWeakPasswordException,
            FirebaseAuthInvalidCredentialsException,
            FirebaseAuthUserCollisionException
    {
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG,"Register success");
                        } else {
                            Log.d(TAG,"Register failed");
                        }
                    }
                });
    }

}
