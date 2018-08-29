package com.example.propertymanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedNo = receivedIntent.getStringExtra("number");
        selectedPostcode = receivedIntent.getStringExtra("postcode");
        selectedAddress = receivedIntent.getStringExtra("address");
        selectedTown = receivedIntent.getStringExtra("town");

        houseNumberInput.setText(selectedNo);
        postcodeNumberInput.setText(selectedPostcode);
        addressNumberInput.setText(selectedAddress);
        townNumberInput.setText(selectedTown);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String houseNumber_et = houseNumberInput.getText().toString();
                String postcode_et = houseNumberInput.getText().toString();
                String address_et = houseNumberInput.getText().toString();
                String town_et = houseNumberInput.getText().toString();

                if (!houseNumber_et.equals("") ||!postcode_et.equals("")
                        ||!address_et.equals("") ||!town_et.equals("")  ){

                    mDatabaseHelper.updateNumber(houseNumber_et, selectedID, selectedNo);
                    mDatabaseHelper.updatePostcode(postcode_et, selectedID, selectedPostcode);
                    mDatabaseHelper.updateAddress(address_et, selectedID, selectedAddress);
                    mDatabaseHelper.updateTown(town_et, selectedID, selectedTown);

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
                Toast.makeText(EditPropertyActivity.this, "Remove From Database", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
