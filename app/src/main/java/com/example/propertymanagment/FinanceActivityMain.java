package com.example.propertymanagment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import chart_and_graphs.FinancePieChartActivity;

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
    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private Button dateSetButton;
    private TextView datesetTextView;
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
        dateSetButton = (Button) findViewById(R.id.selectDate_financeMain_btn);
        datesetTextView = (TextView) findViewById(R.id.date_text_financeMain);

        dateSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FinanceActivityMain.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mOnDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //if month equals '0'
                i1 = i1 +1;

                String currentDate = i2 +"/"+  i1 + "/" + i;
                datesetTextView.setText(currentDate);
            }
        };

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
