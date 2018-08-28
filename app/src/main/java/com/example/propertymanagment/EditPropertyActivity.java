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

    DatabaseHelper mDatabaseHelper;
    //extras
    private int selectedID;
    private String selectedNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        saveButton = (Button) findViewById(R.id.saveButton_editProp);
        deleteButton = (Button) findViewById(R.id.deleteButton_editProp);
        houseNumberInput = (EditText) findViewById(R.id.houseNumber_input);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);
        selectedNo = receivedIntent.getStringExtra("number");

        houseNumberInput.setText(selectedNo);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String houseNumber_et = houseNumberInput.getText().toString();

                if (!houseNumber_et.equals("")){

                    mDatabaseHelper.updateNumber(houseNumber_et, selectedID, selectedNo);
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
