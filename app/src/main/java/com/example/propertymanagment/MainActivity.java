package com.example.propertymanagment;

import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private ViewPager mViewPager;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout mTabLayout;

    private DatabaseReference mUserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setTitle("Chats");

        //tab viewpager
        mViewPager = (ViewPager) findViewById(R.id.mainPager);
        mSectionsPagerAdapter  = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (mAuth.getCurrentUser() != null) {

            mUserRef = FirebaseDatabase.getInstance().getReference().child(mAuth.getCurrentUser().getUid());

        }
        mTabLayout = (TabLayout) findViewById(R.id.main_tabs);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        // if the user isn't signed in, send them to the 'login activity'
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null){

            sendToStartActivity();
        }else{
            mUserRef.child("online").setValue("true");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser mCurrentUser = mAuth.getCurrentUser();

        if (mCurrentUser != null) {

            mUserRef.child("online").setValue(ServerValue.TIMESTAMP);
        }
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
            Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        if (item.getItemId() == R.id.all_users_item){

            Intent userIntent = new Intent(
                    MainActivity.this, UsersActivity.class);
            startActivity(userIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void sendToStartActivity(){

        Intent startIntent = new Intent(
                MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }
}

