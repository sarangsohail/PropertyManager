package com.example.propertymanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTenantActivity extends AppCompatActivity {

    private EditText rent;
    private EditText deposit;
    private EditText rentDueDate;
    private EditText tenantName;
    private Button add_property_btn;
    private Button saveTenantButton;

    private List<AddingTenants> addingTenantsList = new ArrayList<>();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);

        tenantName = (EditText)findViewById(R.id.tenant_name_edittext);
        rent = (EditText) findViewById(R.id.tenant_rent);
        deposit = (EditText) findViewById(R.id.tenant_deposit);
        rentDueDate = (EditText) findViewById(R.id.rent_date);

        initFirebase();
        addFirebaseEventListener();
        
    }

    private void addFirebaseEventListener() {

        mDatabaseReference.child("Tenants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();



    }
}
