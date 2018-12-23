package com.appone.jordan.quiznow.Account;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.appone.jordan.quiznow.R;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    /**
     * This class allows users to edit their profile. They can access their
     * gallery on their phone to select an image to upload. The image is stored as a
     * bitmap and stored to Firebase's storage.
     *
     * Users can also delete their account which triggers a delete
     * event that fires to Firebase's backend and removes the user's email
     * and password they used on creating their profile.
     *
     * This youtube video : https://www.youtube.com/watch?v=f6hdvMMeVSE&t=278s
     * helped with setting the profile picture.
     *
     */

    /*view variables*/
    private ImageView profileEditImg;
    private Button saveBtn;
    private Button deleteAccount;
    private static final int image_chooser = 101;
    private Uri uriProfilePicture;
    String pPictureURL;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Firebase Set Up
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        // Assign Variables
        profileEditImg = findViewById(R.id.profileImage);
        saveBtn = findViewById(R.id.saveBtn);
        deleteAccount = findViewById(R.id.deleteAccountbtn);

        /**
         * Delete Account Event
         */

        deleteAccount.setOnClickListener(v -> {

            firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        TastyToast.makeText(getApplicationContext(), getString(R.string.account_deleted), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                }
            });

        });

        /*when user presses the image we show the gallery*/
        profileEditImg.setOnClickListener(v -> {
            imageChooserResource();
        });

        /*when the user presses the save button store in firebase*/
        saveBtn.setOnClickListener(v -> {
            saveCurrentUser();
        });
    }

    /**
     * This method is called when the user presses save on the
     * activity. The method triggers a User Profile Request
     * which sends user detail params to Firebase's backend.
     * In this case we are setting the URI of the Profile
     * picture for this particular user.
     */

    private void saveCurrentUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null && pPictureURL != null)
        {
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(pPictureURL))
                    .build();

            user.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        // success updated
                        TastyToast.makeText(getApplicationContext(), getString(R.string.profile_picture_updated), TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


                    }
                }
            });
        }
    }


    /**
     *
     * This method handles our Gallery Selection method. Takes in the following params:
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == image_chooser && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfilePicture = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfilePicture);
                profileEditImg.setImageBitmap(bitmap);

                processImage();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method processes our image and stores the image to Firebase's storage database
     */

    private void processImage() {
        // store to firebase storage bucket
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("profilePictures/"+System.currentTimeMillis() + " .jpg");

        if (uriProfilePicture != null)
        {
            storageReference.putFile(uriProfilePicture)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pPictureURL = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                        }
                    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }


    }

    /**
     * Trigger the choose gallery intent
     */

    private void imageChooserResource()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, getString(R.string.capture_image_event)), image_chooser);
    }
}
