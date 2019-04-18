package com.example.propertymanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private ProgressBar circular_progress;

    private List<AddingTenants> addingTenantsList = new ArrayList<>();
    private ListView listData;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    //selects/holds the tenant when in the listview
    private AddingTenants selectedTenant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tenant);

//        getSupportActionBar().setTitle("Add A New Tenant");
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        tenantName = (EditText) findViewById(R.id.tenant_name_edittext);
        rent = (EditText) findViewById(R.id.tenant_rent);
        deposit = (EditText) findViewById(R.id.tenant_deposit);
        rentDueDate = (EditText) findViewById(R.id.rent_date);
        circular_progress = (ProgressBar)findViewById(R.id.circular_progress);

        listData = (ListView)findViewById(R.id.list_data);
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AddingTenants tenants = (AddingTenants) adapterView.getItemAtPosition(i);
                selectedTenant = tenants;
                tenantName.setText(tenants.getName());
                rent.setText(tenants.getRent());
                deposit.setText(tenants.getDeposit());
                rentDueDate.setText(tenants.getRentDueDate());
            }
        });
        //firebase setup
        initFirebase();
        addFirebaseEventListener();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tenants_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.add_tenant)
        {
            createTenant();
        }
        else if(item.getItemId() == R.id.save_tenant)
        {
            AddingTenants tenant = new AddingTenants(selectedTenant.getUid(),
                    tenantName.getText().toString(),
                    rent.getText().toString(),
                    deposit.getText().toString(),
                    rentDueDate.getText().toString());

            updateTenant(tenant);
        }
        else if(item.getItemId() == R.id.remove_tenant){
            removeTenant(selectedTenant);
        }
        return true;
    }

    private void updateTenant(AddingTenants tenant) {
        //updating the tenant's details
        mDatabaseReference.child("Tenants").child(tenant.getUid()).child("name").setValue(tenant.getName());
        mDatabaseReference.child("Tenants").child(tenant.getUid()).child("rent").setValue(tenant.getRent());
        mDatabaseReference.child("Tenants").child(tenant.getUid()).child("deposit").setValue(tenant.getDeposit());
        mDatabaseReference.child("Tenants").child(tenant.getUid()).child("dueDate").setValue(tenant.getRentDueDate());

        clearAllText();
        Toast.makeText(this, "Tenant Updated", Toast.LENGTH_SHORT).show();
    }

    private void createTenant() {

        AddingTenants tenant = new AddingTenants(UUID.randomUUID().toString(), tenantName.getText().toString(),
                rent.getText().toString(), deposit.getText().toString(), rentDueDate.getText().toString());

        mDatabaseReference.child("Tenants").child(tenant.getUid()).setValue(tenant);

        clearAllText();
        Toast.makeText(this, "Tenant Added", Toast.LENGTH_SHORT).show();
    }

    private void clearAllText() {
        tenantName.setText("");
        rent.setText("");
        deposit.setText("");
        rentDueDate.setText("");

    }

    private void addFirebaseEventListener() {

        circular_progress.setVisibility(View.VISIBLE);
        listData.setVisibility(View.INVISIBLE);

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

                circular_progress.setVisibility(View.INVISIBLE);
                listData.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void removeTenant(AddingTenants tenant) {
        try {
            mDatabaseReference.child("Tenants").child(tenant.getUid()).removeValue();
            clearAllText();

            Toast.makeText(this, "Tenant Deleted", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was a problem removing the tenant, try again.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

    }
}
