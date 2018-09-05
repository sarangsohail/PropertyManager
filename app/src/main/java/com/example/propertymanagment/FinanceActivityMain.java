package com.example.propertymanagment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinanceActivityMain extends AppCompatActivity implements InsuranceDialog.InsuranceDialogListener, BillsDialog.billDialogListener{

    private TextView insuranceTextView;
    private Button changeInsuranceBtn;
    private TextView billTextView;
    private Button changeBillsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_main);

        insuranceTextView = (TextView) findViewById(R.id.insurance_display_financeMain);
        changeInsuranceBtn = (Button) findViewById(R.id.insurance_dialog_button);
        billTextView = (TextView) findViewById(R.id.bills_display_financeMain);
        changeBillsBtn = (Button) findViewById(R.id.bills_dialog_Button);

        changeInsuranceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInsuranceDialog();

            }
        });
        changeBillsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBillsDialog();
            }
        });

    }

    private void openInsuranceDialog(){
        InsuranceDialog insuranceDialog = new InsuranceDialog();
        insuranceDialog.show(getSupportFragmentManager(), "insurance dialog");
    }


    private void openBillsDialog(){
        BillsDialog billsDialog = new BillsDialog();
        billsDialog.show(getSupportFragmentManager(), "bills dialog");
    }
    @Override
    public void applyInsurance(String insurance) {
        insuranceTextView.setText("£" + insurance);
    }

    @Override
    public void setBill(String bills) {
        billTextView.setText("£" + bills);
    }
}
