package com.appone.jordan.quiznow.Account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.appone.jordan.quiznow.HomeActivity;
import com.appone.jordan.quiznow.Models.RegisteredUser;
import com.appone.jordan.quiznow.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    /**
     * This Activity handles the Registration for the user.
     * This class sets up a user account with Firebase's Auth services
     * and queries Firebase's Real time database. In the Real Time Database
     * a user node is created which contains: User Score, Username
     */

    /*field variables*/
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmInput;
    private EditText usernameInput;
    private Button registerBtn;

    /*firebase variables*/
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*set title to blank*/
        Objects.requireNonNull(getSupportActionBar()).setTitle("");

        /*assign variables*/
        emailInput = findViewById(R.id.emailRegisterText);
        passwordInput = findViewById(R.id.passwordRegisterText);
        confirmInput = findViewById(R.id.confirmPasswordRegisterText);
        registerBtn = findViewById(R.id.registerBtn);
        usernameInput = findViewById(R.id.usernameField);


        /*init firebase object*/
        firebaseAuth = FirebaseAuth.getInstance();

        /*on click listener*/
        registerBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String confirmPassword = confirmInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();

            setUpUserAccount(email, password, confirmPassword, username);
        });
    }

    /**
     * This method takes in the following params and creates a user account within Firebase, it also creates
     * a user node within the Real time database.
     *
     * @param email
     * @param password
     * @param confirmPassword
     * @param username
     */

    public void setUpUserAccount(String email, String password, String confirmPassword, String username)
    {
        // temp score for user
        int score = 0;
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    RegisteredUser rUser = new RegisteredUser(
                            username, score
                    );
                    // create node in real time database
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(rUser)
                            .addOnCompleteListener(task1 -> {
                                // if node complete re direct
                                if (task1.isSuccessful()) {
                                    TastyToast.makeText(getApplicationContext(), getString(R.string.welcome_message), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                }
                            });
                }else{

                    TastyToast.makeText(getApplicationContext(), getString(R.string.oh_no_message), TastyToast.LENGTH_LONG, TastyToast.ERROR);


                }
            }
        });
    }
}
