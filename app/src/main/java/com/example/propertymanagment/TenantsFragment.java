package com.example.propertymanagment;


import android.app.Activity;
import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TenantsFragment extends Fragment {

    private static final String TAG = "TenantsFragment";
    private RecyclerView mTenantsList;

    private DatabaseReference mTenantsDatabase;
    private DatabaseReference mUsersDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;

    DatabaseReference mUserRef;


    public TenantsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.activity_friends_fragment, container, false);

        mTenantsList = (RecyclerView) mMainView.findViewById(R.id.TenantsRV);

        mAuth = FirebaseAuth.getInstance();


        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mTenantsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(mCurrent_user_id);
        mTenantsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mTenantsList.setHasFixedSize(true);
        mTenantsList.setLayoutManager(new LinearLayoutManager(getContext()));

        return mMainView;
    }

    @Override
    public void onStart() {
        super.onStart();

        Toast.makeText(getContext(), "Before recycler options", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "before recylcer options");
        FirebaseRecyclerOptions<Tenants> options =
                new FirebaseRecyclerOptions.Builder<Tenants>()
                        .setQuery(mUsersDatabase, Tenants.class)
                        .build();

        Toast.makeText(getContext(), "after recycler options", Toast.LENGTH_SHORT).show();

        Log.d(TAG, "after recylcer options");
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<Tenants, TenantsViewHolder>(options) {
            @Override
            public TenantsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.users_single_layout, parent, false);

                Toast.makeText(getContext(), "inside oncreate holder", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "inside oncreate holder");
                return new TenantsViewHolder(view);
            }

            //old recycler code before 29th of Jan
//        FirebaseRecyclerAdapter<Tenants, TenantsViewHolder> adapter =
//                new FirebaseRecyclerAdapter<Tenants, TenantsViewHolder>(options) {
//
//            @NonNull
//            @Override
//            public TenantsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//
//                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.users_single_layout, viewGroup, false);
//                Log.d(TAG , "after tenants view holder");
//
//                //TODO - think this is the bug i think above - it crashes after the comment
//                return new TenantsViewHolder(view);
//
//            }

            @Override
            protected void onBindViewHolder(final TenantsViewHolder tenantsViewHolder, int i, @NonNull Tenants tenants) {
                Log.d(TAG, "inside bindViewholder");


                tenantsViewHolder.setDate(tenants.getDate());

                final String list_user_id = getRef(i).getKey();

                    mUsersDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                                final String userName = dataSnapshot.child("name").getValue().toString();

                              //  final String userStatus = dataSnapshot.child("status").getValue().toString();


                            //    tenantsViewHolder.setStatus(userStatus);
                            // friendsViewHolder.setUserImage(userThumb, getContext());
                            tenantsViewHolder.setName(userName);

                            tenantsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                    //on user click options
                                    CharSequence options[] = new CharSequence[]{"Open Profile", "Send message"};

                                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                    builder.setTitle("Select Options");
                                    builder.setItems(options, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            //event for each option user clicks on the user
                                            if (i == 0) {

                                                Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                                                profileIntent.putExtra("user_id", list_user_id);
                                                startActivity(profileIntent);

                                            }

                                            if (i == 1) {

                                                Intent chatIntent = new Intent(getContext(), ChatActivity.class);
                                                chatIntent.putExtra("user_id", list_user_id);
                                                // chatIntent.putExtra("user_name", userName);
                                                startActivity(chatIntent);

                                            }

                                        }
                                    });
                                    builder.show();
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                            Log.d("database error", databaseError.toString());
                        }
                    });
                }


        };

        mTenantsList.setAdapter(adapter);
        adapter.startListening();

    }


    public static class TenantsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public TenantsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDate(String date) {

            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(date);

        }

        public void setName(String name) {

            TextView userNameView = (TextView) mView.findViewById(R.id.user_single_name);
            userNameView.setText(name);

        }

        public void setStatus(String status){
            TextView userStatusView = (TextView) mView.findViewById(R.id.user_single_status);
            userStatusView.setText(status);
        }

        //yet to do
//        public void setUserImage(String thumb_image, Context ctx){
//
//            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.user_single_image);
//            Picasso.get().load(thumb_image).placeholder(R.drawable.default_profile_pic).into(userImageView);
//
//        }


    }
}
