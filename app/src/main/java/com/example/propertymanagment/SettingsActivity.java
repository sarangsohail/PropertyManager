package com.example.propertymanagment;

import android.content.Intent;
import android.location.Geocoder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import java.nio.channels.GatheringByteChannel;
import java.util.Random;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    private CircleImageView mCircleImageView;
    private TextView mName;
    private TextView mStatus;

    private Button changeStatusButton;
    private Button mImageChangeButton;
    private ProgressBar mProgressBar;

    public static final int GALLERY_PICK = 1;

    //private storage
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCircleImageView = (CircleImageView) findViewById(R.id.settings_image);
        mName = (TextView) findViewById(R.id.settings_name);
        mStatus = (TextView) findViewById(R.id.setting_status);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBarSettings);
        mProgressBar.setVisibility(View.INVISIBLE);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        changeStatusButton = (Button) findViewById(R.id.settings_change_status_button);
        mImageChangeButton = (Button) findViewById(R.id.settings_change_image_button);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        String currentUID = mCurrentUser.getUid();


        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUID);
        mUserDatabase.keepSynced(true);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child("name").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();

                mName.setText(name);
                mStatus.setText(status);

                //todo - default picture works, but not when changed
                if (!image.equals("default"))
                {
                    Picasso.get().load(image).into(mCircleImageView);
                }
                else {
                    Picasso.get().load(image).placeholder(R.drawable.default_profile_pic).into(mCircleImageView);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String status_value = mStatus.getText().toString();

                Intent statusIntent = new Intent(SettingsActivity.this, StatusActivity.class);
                statusIntent.putExtra("status_value", status_value);
                startActivity(statusIntent);
            }
        });

        mImageChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //alternative cropping method i may use
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(SettingsActivity.this);

                //when 'profile pic' button is pressed, load the gallery to select one
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String current_user_ID = "";

        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri imageURI = data.getData();

            current_user_ID =  mCurrentUser.getUid();


            CropImage.activity(imageURI)
                    .setAspectRatio(1, 1)
                    .start(this);


//            Toast.makeText(SettingsActivity.this, imageURI, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mProgressBar.setVisibility(View.VISIBLE);
                Uri resultUri = result.getUri();

                final StorageReference filepath = mStorageRef.child("profile_images").child(current_user_ID + "jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()){


                            String download_url = task.getResult().getDownloadUrl().toString();

                            mUserDatabase.child("image").setValue(download_url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        mProgressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(SettingsActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                            Toast.makeText(SettingsActivity.this, "working", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(SettingsActivity.this, "There was an problem uploading your image", Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();

            }
        }
    }

}
