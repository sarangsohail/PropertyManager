package com.example.propertymanagment;

import android.content.Context;
import android.media.Image;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String mUserChat;
    private DatabaseReference mRootRef;

    public static final String TAG = "ChatActivity";
    private Toolbar mToolbar;

    private FirebaseAuth maAuth;

    private TextView mTitleView;
    private TextView lastSeenView;

    private ImageButton mChatAddButton;
    private ImageButton mSendButton;
    private EditText mChatMessageView;

    private RecyclerView mMessagesList;

    private final List<Messages> messagesLists = new ArrayList<>();
    private LinearLayoutManager mLinearLayout;
    private MessageAdapter messageAdapter;

    private static final int TOTAL_ITEMS_TO_LOAD = 10;
    private int currentPageNo = 1;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    //for message positioning
    private int itemPos = 0;


    private String mLastKey ="";
    private String prevKey ="";

    String mCurrentUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_activity);

//        mToolbar = (Toolbar) findViewById(R.id.chat_app_bar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);


        mUserChat = getIntent().getStringExtra("user_id");

        //setting the actionbar to the tenant's/friend's name
        final String user_chat_name = getIntent().getStringExtra("user_name");
//        getSupportActionBar().setTitle(user_chat_name);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View action_bar_view = layoutInflater.inflate(R.layout.chat_custom_bar, null);

        actionBar.setCustomView(action_bar_view);

        //custom chat bar view items
        mTitleView = (TextView) findViewById(R.id.custom_chat_name);
        lastSeenView = (TextView) findViewById(R.id.custom_bar_seen);

        mSendButton = (ImageButton) findViewById(R.id.chat_send_btn);
        mChatMessageView = (EditText) findViewById(R.id.chat_message_view);

        messageAdapter = new MessageAdapter(messagesLists);

        mMessagesList = (RecyclerView) findViewById(R.id.messages_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_swipe_layout);
        mLinearLayout = new LinearLayoutManager(this);

        mMessagesList.setHasFixedSize(true);
        mMessagesList.setLayoutManager(mLinearLayout);

        mMessagesList.setAdapter(messageAdapter);


        maAuth = FirebaseAuth.getInstance();
        mCurrentUserID = maAuth.getUid();
        mRootRef = FirebaseDatabase.getInstance().getReference();

        mTitleView.setText(user_chat_name);
        loadMessages();

        mRootRef.child("Users").child(mUserChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //todo - sort this last seen thing out
//                 getTimeAgo = new GetTimeAgo();
//
//                long lasttime = Long.parseLong(online);
//
//                String lastSeenTime = getTimeAgo.getTimeAgo(lasttime, getApplicationContext());

              //  lastSeenView.setText(lastSeenTime);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mRootRef.child("Chat").child(mCurrentUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.hasChild(mUserChat)){

                    // values i want to be stored in the chat
                    Map chatAddMap = new HashMap<>();
                    chatAddMap.put("seen", false);
                    chatAddMap.put("timestamp", ServerValue.TIMESTAMP);

                    // storing the map above in both mine and the tenant's chat
                    Map chatUserMap = new HashMap();
                    chatUserMap.put("Chat/" + mCurrentUserID + "/" + mUserChat, chatAddMap);
                    chatUserMap.put("Chat/" + mUserChat + "/" + mCurrentUserID, chatAddMap);

                    mRootRef.updateChildren(chatUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        }
                    });
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //onClickListens for the chat view views
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }


            private void sendMessage() {

                String message = mChatMessageView.getText().toString();

                if (!TextUtils.isEmpty(message)){

                    String currentUserRef = "messages/" + mCurrentUserID + "/" + mUserChat;
                    String chat_user_ref = "messages/" + mUserChat + "/" + mCurrentUserID;

                    //pushId for the message
                    DatabaseReference user_message_push = mRootRef.child("messages/").child(mCurrentUserID)
                            .child(mUserChat).push();

                    String push_id = user_message_push.getKey();

                    //information for the message being sent
                    Map messageMap = new HashMap();
                    messageMap.put("message", message);
                    messageMap.put("seen", false);
                    messageMap.put("type", "text");
                    messageMap.put("time", ServerValue.TIMESTAMP);
                    messageMap.put("from", mCurrentUserID);


                    Map messageUserMap = new HashMap();
                    messageUserMap.put(currentUserRef + "/" + push_id, messageMap);
                    messageUserMap.put(chat_user_ref + "/" + push_id, messageMap);

                    mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if (databaseError != null){
                                Log.d("CHAT_LOG", databaseError.getMessage().toString());


                            }
                        }
                    });

                    mChatMessageView.setText("");
                }

            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                currentPageNo++;

                itemPos = 0;

                loadMoreMessages();
                }
        });
    }

    private void loadMoreMessages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrentUserID).child(mUserChat);

        Query messageQuery = messageRef.orderByKey().endAt(mLastKey).limitToLast(10);

        //get and display the messages + load refresh, show the last then in the correct order

        messageQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Messages messages = dataSnapshot.getValue(Messages.class);
                String messageKey = dataSnapshot.getKey();
                messagesLists.add(itemPos++, messages);

                //remove the repeating messages when refreshing
                if (!prevKey.equals(messageKey)) {

                    messagesLists.add(itemPos, messages);

                }else{

                    prevKey = mLastKey;
                }
                if (itemPos == 1){

                    mLastKey = messageKey;
                }

                messageAdapter.notifyDataSetChanged();

                mSwipeRefreshLayout.setRefreshing(false);

                mLinearLayout.scrollToPositionWithOffset(10, 0  );

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    private void loadMessages() {

        DatabaseReference messageRef = mRootRef.child("messages").child(mCurrentUserID).child(mUserChat);

        final Query messageQuery = messageRef.limitToLast(currentPageNo * TOTAL_ITEMS_TO_LOAD);

        messageQuery.addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Messages messages = dataSnapshot.getValue(Messages.class);

                        itemPos++;
                        String messageKey = dataSnapshot.getKey();


                    if (itemPos == 1){


                        mLastKey = messageKey;
                        prevKey = messageKey;
                    }




                        messagesLists.add(messages);
                        messageAdapter.notifyDataSetChanged();

                        //scroll to the latest message
                    mMessagesList.scrollToPosition(messagesLists.size() -1);

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


}

//