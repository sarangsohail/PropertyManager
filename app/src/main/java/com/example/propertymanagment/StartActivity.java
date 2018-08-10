package com.example.propertymanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button mButtonReg;
    private Button mButtonSign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mButtonReg = (Button) findViewById(R.id.start_reg_button);
        mButtonSign = (Button) findViewById(R.id.already_have_button_start);


        mButtonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(StartActivity.this, RegisterActivity.class);
                startActivity(regIntent);
            }
        });

        mButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(loginIntent);

            }
        });

    }
}
