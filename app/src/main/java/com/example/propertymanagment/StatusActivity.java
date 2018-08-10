package com.example.propertymanagment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText mStatus;
    private Button mSaveButton;

    //firebase ref
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = mCurrentUser.getUid();

        mStatusDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBarStatusActivity);
        mProgressBar.setVisibility(View.INVISIBLE);
        getSupportActionBar().setTitle("Account Status");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String status_value = getIntent().getStringExtra("status_value");

        mStatus = (TextInputEditText) findViewById(R.id.statusActivity_statusInput);
        mSaveButton = (Button) findViewById(R.id.statusActivity_statusSaveChanges_button);

        mStatus.setText(status_value);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProgressBar.setVisibility(View.VISIBLE);
                String status = mStatus.getText().toString();

                mStatusDatabase.child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){

                            mProgressBar.setVisibility(View.INVISIBLE);

                            Toast.makeText(StatusActivity.this, "Status Changed", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{

                            Toast.makeText(StatusActivity.this, "There was an error whilst saving the changes", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            };
        });
    }
}
