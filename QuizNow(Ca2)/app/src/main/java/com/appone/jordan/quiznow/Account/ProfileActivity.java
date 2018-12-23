package com.appone.jordan.quiznow.Account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.appone.jordan.quiznow.Models.RegisteredUser;
import com.appone.jordan.quiznow.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ProfileActivity extends AppCompatActivity {

    /**
     * This Activity displays the current logged
     * in user's details. It makes an API call to our
     * Firebase back end and returns the current user name,
     * user profile picture and the user score.
     */

    // Firebase Variables

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener authStateListener;

    /*View variables*/
    private TextView profileName;
    private TextView joinedDate;
    private TextView userScore;
    private ImageView profilePicture;
    private Button editProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editProfile = findViewById(R.id.editProfile);

        editProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, EditProfileActivity.class));
            finish();
        });


        /*listenner*/
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        /**
         * This listener listens our for changes to our Database
         * instance and captures it in a dataSnapshot object which
         * is used later on in this code base.
         */

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getUserData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * This method takes in a data snapshot from our event
     * listener above in the onCreate method. We use the dataSnapshot of
     * our database to capture the username and current score for this current
     * user. There is a bug here with display the current user profile image so
     * code for displaying the current user profile image has being removed.
     *
     * @param dataSnapshot
     */

    private void getUserData(DataSnapshot dataSnapshot)
    {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        profileName = findViewById(R.id.profileActivityName);

        joinedDate = findViewById(R.id.joinedDateField);

        userScore = findViewById(R.id.uScoreField);

        profilePicture = findViewById(R.id.profilePicture);

        for (DataSnapshot d : dataSnapshot.getChildren())
        {
            // read
            RegisteredUser u = new RegisteredUser();
            u.setUsername(d.child(userID).getValue(RegisteredUser.class).getUsername());
            u.setUserScore(d.child(userID).getValue(RegisteredUser.class).getUserScore());

            String uName = u.getUsername();
            int uScore = u.getUserScore();
            String uDate = getString(R.string.date_temp);

            userScore.setText(String.valueOf(uScore));

            profileName.setText(uName);

            joinedDate.setText(uDate);
        }
    }
}
