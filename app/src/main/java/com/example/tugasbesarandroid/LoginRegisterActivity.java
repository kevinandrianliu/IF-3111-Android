package com.example.tugasbesarandroid;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class LoginRegisterActivity extends AppCompatActivity implements RegisterFragment.RegisterListener, LoginFragment.LoginListener {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginregister);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager;
            FragmentTransaction fragmentTransaction;

            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.loginRegisterFragmentContainer, new LoginFragment());
            fragmentTransaction.commit();
        }
    }


    public void switchToRegister(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        fragmentTransaction.replace(R.id.loginRegisterFragmentContainer, new RegisterFragment());
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LoginRegisterActivity.class.getSimpleName(),"LoginRegisterActivity destoryed");
    }

    @Override
    public void onRegisterSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
