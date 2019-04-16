package com.example.propertymanagment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PropertyActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;

    //property views
     Button saveButton;
     Button all_Prop_button;
     EditText house_number_view;
     EditText postcode_number_view;
     EditText address_number_view;
     EditText town_number_view;
     EditText rent_number_view;

    String newHouseNo;
    String newPostcode;
    String newAddress;
    String newTown;
    String newRent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        getSupportActionBar().setTitle("Add Property");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //all editext referencing
        house_number_view = (EditText) findViewById(R.id.prop_house_number_et);
        postcode_number_view = (EditText) findViewById(R.id.postcode_add_prop);
        address_number_view = (EditText) findViewById(R.id.prop_street_et);
        town_number_view = (EditText) findViewById(R.id.city_town_et);
        rent_number_view = (EditText) findViewById(R.id.rent_add_prop);

        saveButton = (Button) findViewById(R.id.prop_button_save);
        all_Prop_button = (Button) findViewById(R.id.view_all_properties_button);

        databaseHelper = new DatabaseHelper(this);

        //all the spinners for the 'add property' class

        Spinner paymentSpinner = (Spinner) findViewById(R.id.payment_frequency_spinner);
        Spinner bedroomSpinner = (Spinner) findViewById(R.id.bedroom_spinner_add_prop);
        Spinner bathroomSpinner = (Spinner) findViewById(R.id.bathroom_spinner_add_prop);


        ArrayAdapter<CharSequence> paymentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.payment_frequency_array_add_prop, android.R.layout.simple_spinner_item);
        paymentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentSpinnerAdapter);


        ArrayAdapter<CharSequence> bedroomSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.bedroom_add_prop, android.R.layout.simple_spinner_item);
        bedroomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bedroomSpinner.setAdapter(bedroomSpinnerAdapter);

        ArrayAdapter<CharSequence> bathroomSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.bathroom_add_prop, android.R.layout.simple_spinner_item);
        bedroomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bathroomSpinner.setAdapter(bedroomSpinnerAdapter);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 newHouseNo = house_number_view.getText().toString();
                 newPostcode = postcode_number_view.getText().toString();
                 newAddress = address_number_view.getText().toString();
                 newTown = town_number_view.getText().toString();
                 newRent = rent_number_view.getText().toString();
//                String newRent = Integer.toString(rent_number_view.getText().toString())

                if (newHouseNo.length() != 0 && newPostcode.length() != 0 && newAddress.length() != 0 && newTown.length() != 0 && newRent.length() != 0){
                    AddData(newHouseNo, newPostcode, newAddress, newTown, newRent);

                    house_number_view.setText("");
                    postcode_number_view.setText("");
                    address_number_view.setText("");
                    town_number_view.setText("");
                    rent_number_view.setText("");

                }else{
                    Toast.makeText(PropertyActivity.this, "problem sending storing your data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        all_Prop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent allPropertiesIntent = new Intent(PropertyActivity.this, AllProperties.class);
                    allPropertiesIntent.putExtra("postcode", newPostcode);
                    allPropertiesIntent.putExtra("address", newAddress);
                    allPropertiesIntent.putExtra("town", newTown);
                    allPropertiesIntent.putExtra("rent", newRent);
                    startActivity(allPropertiesIntent);


            }
        });
    }



    public void AddData(String houseNo, String postcode, String address, String town, String rent){
        boolean insertData =  databaseHelper.addData(houseNo, postcode, address, town, rent);

        if (insertData){
            Toast.makeText(this, "Property Successfully Added", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "A problem occurred with your input, however data was inputted!", Toast.LENGTH_SHORT).show();

        }

    }
}
