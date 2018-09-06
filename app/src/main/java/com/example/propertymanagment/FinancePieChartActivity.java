package com.example.propertymanagment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FinancePieChartActivity extends AppCompatActivity {

    public static final String TAG = "FinancePieChart";

    //private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
//    private float[] yData = {50,20,15};
    ArrayList<Float> yData = new ArrayList<Float>();
    //private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};
    PieChart pieChart;
    private float insuranceFloat;
    private float mortgageFloat;
    private float billsFloat;


    String pieDesc = "";
    private Float insurance, bills, mortgage, legal, repairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_pie_chart);

        Intent piechartIntent = getIntent();

         insurance = piechartIntent.getFloatExtra("insurance", 0);
         mortgage =  piechartIntent.getFloatExtra("mortgage",0);
         bills = piechartIntent.getFloatExtra("bills", 0);
        Log.d(TAG, "string mortgage iis ...  " + mortgage);

        yData.add(0, insurance);
        yData.add(1, mortgage);
        yData.add(2, bills);
//        for(int i=0; i<yData.length; i++){
//            yData[i] = insurance;
//            yData[1] = mortgage;
//
//        }
        insuranceFloat = insurance;
        mortgageFloat= mortgage;
        billsFloat = bills;
        Log.d(TAG, "in the oncreate before the piechart config" + mortgageFloat+ "insurance float" + insuranceFloat);
//        //pie chart apperance config
        pieChartConfig();

        addDataSet();
    }


    private void pieChartConfig() {
        pieChart = (PieChart) findViewById(R.id.financePieChart);

        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Property Costs");
        pieChart.setCenterTextSize(13);
    }

    private void addDataSet() {
        Log.d(TAG, "in data set method: ");

        ArrayList<PieEntry> yEntries = new ArrayList<>();
    //    ArrayList<String> xEntrys = new ArrayList<>();

//        for(int i = 0; i < yData.size() -1; i++){
            yEntries.add(new PieEntry( yData.set(0,insuranceFloat )));
            yEntries.add(new PieEntry(yData.set(1, mortgageFloat )));
            yEntries.add(new PieEntry(yData.set(2, billsFloat)));
//        }

//        for (int i=0; i<xData.length; i++){
//            xEntrys.add(xData[i]);
//        }

        //creating the dataset
        PieDataSet pieDataSet = new PieDataSet(yEntries, "Property costs'");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);

        pieDataSet.setColors(colors);

        //the key for values
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

}
