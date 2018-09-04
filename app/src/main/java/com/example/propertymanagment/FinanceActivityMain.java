package com.example.propertymanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinanceActivityMain extends AppCompatActivity implements InsuranceDialog.InsuranceDialogListener{

    private TextView insuranceDisplayTV;
    private Button changeFinancesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_main);

        insuranceDisplayTV = (TextView) findViewById(R.id.insurance_display_financeMain);
        changeFinancesButton = (Button) findViewById(R.id.call_dialogButton);

        changeFinancesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsuranceDialog();

            }
        });
    }

    private void openInsuranceDialog(){
        InsuranceDialog insuranceDialog = new InsuranceDialog();
        insuranceDialog.show(getSupportFragmentManager(), "insurance dialog");
    }

    @Override
    public void applyInsurance(String insurance) {
        insuranceDisplayTV.setText(insurance);

    }
}
