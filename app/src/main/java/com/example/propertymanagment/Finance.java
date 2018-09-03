package com.example.propertymanagment;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;

public class Finance extends AppCompatActivity {

    public static final String TAG = "FinanceActivity";

    //adds points to the graph from the datapoint

    PointsGraphSeries<DataPoint> xySeries;

    private Button addPointsButtonFinance;
    private EditText mX, mY;

    GraphView mScatterPlot;


    //making the arrayList global
    private ArrayList<XYValues> xyValueArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance);

        addPointsButtonFinance = (Button) findViewById(R.id.btnAddPt);
        mX = (EditText) findViewById(R.id.numX);
        mY = (EditText) findViewById(R.id.numY);
        mScatterPlot = (GraphView) findViewById(R.id.scatterPlot);
        xyValueArray = new ArrayList<>();

        init();
    }

    //want this to run after every new plotting of points
    private void init() {

        xySeries = new PointsGraphSeries<>();

        addPointsButtonFinance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mX.getText().toString().equals("") && !mY.getText().toString().equals("")){

                    double x = Double.parseDouble(mX.getText().toString());
                    double y = Double.parseDouble(mY.getText().toString());
                    xyValueArray.add(new XYValues(x,y));
                    //calls itself to refresh the points
                    init();
                }else {
                    Toast.makeText(Finance.this, "Fill in all the fields!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //error handling for if there is no data to add to the graph
        if (xyValueArray.size() != 0){
            createScatterPlot();
        }else {
            Log.d(TAG, "no data to add");
        }
    }

    private void createScatterPlot() {

        //sort out the array of xy values in ascending order 
        xyValueArray = sortArray(xyValueArray);

        //add the data to the series
        for (int i=0; i <xyValueArray.size(); i++){

            try {
                double x = xyValueArray.get(i).getX();
                double y = xyValueArray.get(i).getY();
                xySeries.appendData(new DataPoint(x,y), true, 1000);
            }catch (IllegalArgumentException e){
                Log.e(TAG, e.toString());
            }
        }
        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.BLUE);

        xySeries.setSize(20f);

        //set Scrollable and scalable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxY(150);
        mScatterPlot.getViewport().setMinY(-150);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);
        mScatterPlot.getViewport().setMaxX(150);
        mScatterPlot.getViewport().setMinX(-150);

        mScatterPlot.addSeries(xySeries);
    }

    private ArrayList<XYValues> sortArray(ArrayList<XYValues> array){
        /*
           Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size() - 1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while (true) {
            m--;
            if (m <= 0) {
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try {
                //print out the y entries so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m - 1).getY();
                double tempX = array.get(m - 1).getX();
                if (tempX > array.get(m).getX()) {
                    array.get(m - 1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m - 1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                } else if (tempX == array.get(m).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                } else if (array.get(m).getX() > array.get(m - 1).getX()) {
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if (count == factor) {
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }

}
