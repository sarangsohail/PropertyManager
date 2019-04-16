package com.example.propertymanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditPropertyActivity extends AppCompatActivity {

    private Button saveButton;
    private Button deleteButton;
    private EditText houseNumberInput;
    private EditText postcodeNumberInput;
    private EditText addressNumberInput;
    private EditText townNumberInput;
    private EditText rentNumberInput;


    DatabaseHelper mDatabaseHelper;
    //extras
    private int selectedID;
    private String selectedNo;
    private String selectedPostcode;
    private String selectedAddress;
    private String selectedTown;
    private String selectedRent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        saveButton = (Button) findViewById(R.id.saveButton_editProp);
        deleteButton = (Button) findViewById(R.id.deleteButton_editProp);
        houseNumberInput = (EditText) findViewById(R.id.houseNumber_input);
        postcodeNumberInput = (EditText) findViewById(R.id.postcode_editProperty_et);
        addressNumberInput = (EditText) findViewById(R.id.property_address_edit_et);
        townNumberInput = (EditText) findViewById(R.id.property_town_et);
        rentNumberInput = (EditText) findViewById(R.id.property_rent_editProp_et);

        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedNo = receivedIntent.getStringExtra("number");
        selectedPostcode = receivedIntent.getStringExtra("postcode");
        selectedAddress = receivedIntent.getStringExtra("address");
        selectedTown = receivedIntent.getStringExtra("town");
        selectedRent = receivedIntent.getStringExtra("rent");


        houseNumberInput.setText(selectedNo);
        postcodeNumberInput.setText(selectedPostcode);
        addressNumberInput.setText(selectedAddress);
        townNumberInput.setText(selectedTown);
        rentNumberInput.setText(selectedRent);

        //todo - get the rent to display in the edit propertu activity

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String houseNumber_et = houseNumberInput.getText().toString();
                String postcode_et = postcodeNumberInput.getText().toString();
                String address_et = addressNumberInput.getText().toString();
                String town_et = townNumberInput.getText().toString();
                String rent_et = rentNumberInput.getText().toString();

                if (!houseNumber_et.equals("") &&!postcode_et.equals("")
                        &&!address_et.equals("") &&!town_et.equals("") && !rent_et.equals("") ){

                    mDatabaseHelper.updateNumber(houseNumber_et, selectedID, selectedNo);
                    mDatabaseHelper.updatePostcode(postcode_et, selectedID, selectedPostcode);
                    mDatabaseHelper.updateAddress(address_et, selectedID, selectedAddress);
                    mDatabaseHelper.updateTown(town_et, selectedID, selectedTown);
                    mDatabaseHelper.updateRent(rent_et, selectedID, selectedRent);
                    Toast.makeText(EditPropertyActivity.this, "Updated Property Details", Toast.LENGTH_SHORT).show();
                    finish();

                }else{
                    Toast.makeText(EditPropertyActivity.this, "Oops, Missing Value!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mDatabaseHelper.deleteNumber(selectedID, selectedNo);

                houseNumberInput.setText("");
                postcodeNumberInput.setText("");
                addressNumberInput.setText("");
                townNumberInput.setText("");
                rentNumberInput.setText("");
                Toast.makeText(EditPropertyActivity.this, "Successfully Removed Property", Toast.LENGTH_SHORT).show();
                Intent sendBackToPropertyActivity = new Intent(getApplicationContext(), PropertyActivity.class);
                startActivity(sendBackToPropertyActivity);
            }
        });

    }
    //todo - get all the values of the property to remain there, not just the house No.
}
