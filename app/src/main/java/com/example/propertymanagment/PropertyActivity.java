package com.example.propertymanagment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private EditText mPropertyName;
    DatabaseHelper databaseHelper;

    //property views
     Button saveButton;
     Button all_Prop_button;
     EditText house_number_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        getSupportActionBar().setTitle("Add Property");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPropertyName = (EditText) findViewById(R.id.prop_street_et);

        //all editext referencing
        house_number_view = (EditText) findViewById(R.id.prop_house_number_et);
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

                String newHouseNo = house_number_view.getText().toString();

                if (newHouseNo.length() != 0){
                    AddData(newHouseNo);
                    house_number_view.setText("");
                }else{
                    Toast.makeText(PropertyActivity.this, "problem sending storing your data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        all_Prop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allPropertiesIntent = new Intent(PropertyActivity.this, AllProperties.class);
                startActivity(allPropertiesIntent);
            }
        });
    }



    public void AddData(String newEntry){
        boolean insertData =  databaseHelper.addData(newEntry);

        if (insertData){
            Toast.makeText(this, "Data Successfully Added", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

    }
}
