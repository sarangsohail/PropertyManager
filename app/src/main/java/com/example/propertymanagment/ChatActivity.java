package com.example.propertymanagment;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ChatActivity extends AppCompatActivity {

    private String mUserChat;
    private DatabaseReference mRootRef;

    private Toolbar mToolbar;

    private TextView mTitleView;
    private TextView lastSeenView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);

        mToolbar = (Toolbar) findViewById(R.id.chat_app_bar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);


        mUserChat = getIntent().getStringExtra("user_id");

        //setting the actionbar to the tenant's/friend's name
        String user_chat_name = getIntent().getStringExtra("user_name");
//        getSupportActionBar().setTitle(user_chat_name);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.chat_custom_bar, null);

        actionBar.setCustomView(action_bar_view);

        //custom chat bar view items
        mTitleView = (TextView) findViewById(R.id.custom_chat_name);
        lastSeenView = (TextView) findViewById(R.id.custom_bar_seen);

        mRootRef = FirebaseDatabase.getInstance().getReference();

        mTitleView.setText(user_chat_name);

        mRootRef.child("Users").child(mUserChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String online = dataSnapshot.child("online").getValue().toString();

                GetTimeAgo getTimeAgo = null;

                if(online.equals("true")) {

                    lastSeenView.setText(R.string.chat_online_string);

                }else

                 getTimeAgo = new GetTimeAgo();

                long lasttime = Long.parseLong(online);

                String lastSeenTime = getTimeAgo.getTimeAgo(lasttime, getApplicationContext());

                lastSeenView.setText(lastSeenTime);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
