package com.example.propertymanagment;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class FinanceActivityMain extends AppCompatActivity implements InsuranceDialog.InsuranceDialogListener, BillsDialog.billDialogListener, MortageDialog.MortageDialogListener{

    private static final String TAG = "FinanceMainActivity";
    private TextView insuranceTextView;
    private Button changeInsuranceBtn;
    private TextView billTextView;
    private Button changeBillsBtn;
    private TextView mortgageTextView;
    private Button changeMortgageBtn;
    private Button different_format_button;
    private float insurancePieChart;
    private float billsPieChart;
    private float mortgagePieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_main);

        getSupportActionBar().setTitle("Set Your Finances");
        insuranceTextView = (TextView) findViewById(R.id.insurance_display_financeMain);
        changeInsuranceBtn = (Button) findViewById(R.id.insurance_dialog_button);
        billTextView = (TextView) findViewById(R.id.bills_display_financeMain);
        changeBillsBtn = (Button) findViewById(R.id.bills_dialog_Button);
        mortgageTextView = (TextView) findViewById(R.id.mortgage_display_financeMain);
        changeMortgageBtn = (Button) findViewById(R.id.mortgage_dialog_Button);
        different_format_button = (Button) findViewById(R.id.piechartButton_financeMain);



        //button listeners
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
        changeMortgageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMortageDialog();
            }
        });

        different_format_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mortgageTextView.getText().toString().equals("")
                        && !insuranceTextView.toString().equals("")
                        && !billTextView.getText().toString().equals("")) {

                    Intent pieChartIntent =new Intent(FinanceActivityMain.this, FinancePieChartActivity.class);
                    pieChartIntent.putExtra("insurance", insurancePieChart);
                    pieChartIntent.putExtra("mortgage", mortgagePieChart);
                    pieChartIntent.putExtra("bills", billsPieChart);
                    startActivity(pieChartIntent);
                } else {
                    Toast.makeText(FinanceActivityMain.this, "Please fill in all the fields above", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openMortageDialog() {
        MortageDialog mortageDialog = new MortageDialog();
        mortageDialog.show(getSupportFragmentManager(), "mortage dialog");

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
    public void getInsurance(String newInsurance) {
        float changeInsurance = Float.parseFloat(newInsurance);
        insurancePieChart = changeInsurance;
    }


    @Override
    public void setBill(String bills) {
        billTextView.setText("£" + bills);
    }

    @Override
    public void getBill(String newBill) {
        float newChangesBill = Float.parseFloat(newBill);
        billsPieChart = newChangesBill;
    }

    @Override
    public void applyMortgage(String mortgage) {
        mortgageTextView.setText("£" + mortgage);
    }

    @Override
    public void getMortgage(String newMortgage) {
        float changeNewMortgage = Float.parseFloat(newMortgage);
        mortgagePieChart = changeNewMortgage;
    }
}
