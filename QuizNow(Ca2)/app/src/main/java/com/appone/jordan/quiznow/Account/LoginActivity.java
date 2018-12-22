package com.appone.jordan.quiznow.Account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appone.jordan.quiznow.HomeActivity;
import com.appone.jordan.quiznow.R;
import com.danimahardhika.cafebar.CafeBar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdsmdg.tastytoast.TastyToast;

public class LoginActivity extends AppCompatActivity {
    /* Variables Set Up */

    private EditText emailAddressField;
    private EditText passwordField;
    private Button loginBtn;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*INIT Vars*/
        emailAddressField = findViewById(R.id.emailAddress);
        passwordField = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginToNextActivity);

        /* firebase init*/
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

//        /* Check if the user is already logged in */
        authStateListener = firebaseAuth -> {
            if (user != null) {

                CafeBar.builder(LoginActivity.this)
                        .customView(R.layout.logged_in_toast_pass)
                        .floating(true)
                        .show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        };

        /*when user presses signin */
        loginBtn.setOnClickListener(v -> {
            String email = emailAddressField.getText().toString();
            String password = passwordField.getText().toString();

            Log.d("App", "the email address " + email + " and password is " + password);

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                if (!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }else{
                    TastyToast.makeText(getApplicationContext(), "Welcome !", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }
            });
        });

    }

    /* Onn start of activity check if user is logged in*/
    @Override
    protected void onStart()
    {
        super.onStart();


    }

    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
    }
}
