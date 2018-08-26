package com.example.propertymanagment;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PropertyActivity extends AppCompatActivity {

    private EditText mPropertyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        getSupportActionBar().setTitle("Add Property");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPropertyName = (EditText) findViewById(R.id.prop_street_et);

        //all the spinners for the 'add property' class

        Spinner paymentSpinner = (Spinner) findViewById(R.id.payment_frequency_spinner);
        Spinner bedroomSpinner = (Spinner) findViewById(R.id.bedroom_spinner_add_prop);
        ArrayAdapter<CharSequence> paymentSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.payment_frequency_array_add_prop, android.R.layout.simple_spinner_item);
        paymentSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentSpinner.setAdapter(paymentSpinnerAdapter);


        ArrayAdapter<CharSequence> bedroomSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.bedroom_add_prop, android.R.layout.simple_spinner_item);
        bedroomSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bedroomSpinner.setAdapter(bedroomSpinnerAdapter);


    }

}
