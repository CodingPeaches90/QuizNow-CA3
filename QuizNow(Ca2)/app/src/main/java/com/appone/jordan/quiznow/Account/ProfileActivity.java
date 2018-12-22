package com.appone.jordan.quiznow.Account;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.appone.jordan.quiznow.Models.RegisteredUser;
import com.appone.jordan.quiznow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    /*view variables*/
    private TextView profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*firebase variables set up*/
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        /*listenner*/
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("users").child(userID).child("userScore");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RegisteredUser score = dataSnapshot.getValue(RegisteredUser.class);
                TastyToast.makeText(getApplicationContext(), "Welcome ! " + score, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        /*view set up*/
        profileName = findViewById(R.id.profileActivityName);
        profileName.setText(email);



        /*search firebases rtd*/

    }
}
