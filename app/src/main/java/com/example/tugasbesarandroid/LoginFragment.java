package com.example.tugasbesarandroid;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private Button loginButton;
    private EditText emailValue;
    private EditText passValue;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        loginButton = (Button) view.findViewById(R.id.loginButton);
        emailValue = (EditText) view.findViewById(R.id.loginEmailValue);
        passValue = (EditText) view.findViewById(R.id.loginPasswordValue);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(this.getClass().getSimpleName(),"DESTROYED");
    }

    private void doLogin(){
        String email = emailValue.getText().toString();
        String password = passValue.getText().toString();

        boolean emailCheck = isEmailCorrect(email);
        boolean passwordCheck = isPasswordCorrect(password);

        if (emailCheck && passwordCheck){
            // Send data to server
        } else {
            if (!(emailCheck)){
                Log.d(this.getClass().getSimpleName(),"Email false");
            }
            if (!(passwordCheck)){
                Log.d(this.getClass().getSimpleName(),"Password false");
            }
        }
    }

    private boolean isEmailCorrect(String email){
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isPasswordCorrect(String password){
        return (password.length() > 6);
    }
}
