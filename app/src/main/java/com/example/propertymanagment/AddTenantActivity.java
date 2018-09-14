package com.example.propertymanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddTenantActivity extends AppCompatActivity {

    private EditText rent;
    private EditText deposit;
    private EditText rentDueDate;
    private EditText tenantName;
    private Button add_property_btn;
    private Button saveTenantButton;

    private List<AddingTenants> addingTenantsList = new ArrayList<>();
    private ListView listData;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);

        tenantName = (EditText) findViewById(R.id.tenant_name_edittext);
        rent = (EditText) findViewById(R.id.tenant_rent);
        deposit = (EditText) findViewById(R.id.tenant_deposit);
        rentDueDate = (EditText) findViewById(R.id.rent_date);
        listData = (ListView)findViewById(R.id.list_data);

       gi//firebase setup
        initFirebase();
        addFirebaseEventListener();

    }

    private void createTenant() {

        AddingTenants tenant = new AddingTenants(UUID.randomUUID().toString(), tenantName.getText().toString(),
                rent.getText().toString(), deposit.getText().toString(), rentDueDate.getText().toString());

        mDatabaseReference.child("Tenants").child(tenant.getUid()).setValue(tenant);

        clearAllText();
    }

    private void clearAllText() {
        tenantName.setText("");
        rent.setText("");
        deposit.setText("");
        rentDueDate.setText("");

    }

    private void addFirebaseEventListener() {

        mDatabaseReference.child("Tenants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (addingTenantsList.size() > 0){
                    addingTenantsList.clear();
                }
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    AddingTenants tenants = postSnapshot.getValue(AddingTenants.class);
                    addingTenantsList.add(tenants);
                }

                TenantsListViewAdapter adapter = new TenantsListViewAdapter(AddTenantActivity.this, addingTenantsList);
                listData.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();


    }
}
