package com.example.propertymanagment;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private DatabaseReference mUserRef;

    private CardView mProperty, mFinance, mTenant, mChat, mEmergency;

    private Dialog aboutUserMenuDialoag;

    private Button btnAaoutUserMenuDialoag;

    private TextView about_user_main_textview_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //get user display name
        Intent userNameIntent = getIntent();
        String user_name = userNameIntent.getStringExtra("user_name");

//        if (user_name == null){
//            sendToStartActivity();
//        }else{
//            getSupportActionBar().setTitle("Welcome");
//        }

        if (user != null) {
            getSupportActionBar().setTitle("Welcome");
        }else{
            sendToStartActivity();
        }


        //onclick listeners for the main options in main activity
        mProperty = (CardView) findViewById(R.id.add_property_main);
        mFinance = (CardView) findViewById(R.id.finance_option_main);
        mTenant = (CardView) findViewById(R.id.add_tenant_option_main);
        mChat = (CardView) findViewById(R.id.message_option_main);
        mEmergency = (CardView) findViewById(R.id.emergency_option_main);

        //todo - add all the below activities in the manifest
        mProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent propertyIntent = new Intent(MainActivity.this, PropertyActivity.class);
                startActivity(propertyIntent);
            }
        });

        mFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        mTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent loadMessagingActivity = new Intent(MainActivity.this, MessagingMainActivity.class);
                startActivity(loadMessagingActivity);
            }
        });
        mEmergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser == null){

            sendToStartActivity();

        }
    }

    private void sendToStartActivity() {

        Intent startIntent = new Intent(
                MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.main_signout_button){

            FirebaseAuth.getInstance().signOut();

            sendToStartActivity();
        }else if (item.getItemId() == R.id.about_user_button){

            String userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

            aboutUserMenuDialoag = new Dialog(this);
            aboutUserMenuDialoag.setContentView(R.layout.about_popup_main);

            about_user_main_textview_menu = (TextView) findViewById(R.id.about_user_main_textview);
            //about_user_main_textview_menu.setText("Email: " );

            btnAaoutUserMenuDialoag = (Button) findViewById(R.id.btn_ok_about_menu);

            btnAaoutUserMenuDialoag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    aboutUserMenuDialoag.dismiss();
                }
            });
        }

        return super.onOptionsItemSelected(item);

    }
}
