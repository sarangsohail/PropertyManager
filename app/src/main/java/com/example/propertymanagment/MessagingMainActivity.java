package com.example.propertymanagment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MessagingMainActivity extends AppCompatActivity {

    private static final String TAG = "MessagingMainActivity";
    private FirebaseAuth mAuth;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;
    private DatabaseReference mUserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messaging_main_activity);

        mAuth = FirebaseAuth.getInstance();

       // getSupportActionBar().setTitle("Chats");

        if (mAuth.getCurrentUser() != null) {
            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        }else{

            Toast.makeText(this, "user ref problem", Toast.LENGTH_SHORT).show();
        }

        //tab viewpager
        mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mSectionsPagerAdapter  = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d(TAG, "start onStart");

        if(currentUser == null){

            sendToStartActivity();

        }
        Log.d(TAG, "end on of onStart");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.chat_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == R.id.main_signout_button){
            FirebaseAuth.getInstance().signOut();
            sendToStartActivity();
        }

        if (item.getItemId() == R.id.main_settings_menu_button){
            Intent settingsIntent = new Intent(MessagingMainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        if (item.getItemId() == R.id.all_users_item){

            Intent userIntent = new Intent(
                    MessagingMainActivity.this, UsersActivity.class);
            startActivity(userIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendToStartActivity(){

        Intent startIntent = new Intent(
                MessagingMainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }
}

