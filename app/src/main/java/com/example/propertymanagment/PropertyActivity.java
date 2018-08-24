package com.example.propertymanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PropertyActivity extends AppCompatActivity {

    private EditText mPropertyName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property);

        getSupportActionBar().setTitle("Add Property");
        mPropertyName = (EditText) findViewById(R.id.prop_street_et);
    }
}
