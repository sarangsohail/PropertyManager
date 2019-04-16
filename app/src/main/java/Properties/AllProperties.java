package com.example.propertymanagment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class AllProperties extends AppCompatActivity {

    private static final String TAG = "AllProperties";
    DatabaseHelper mDatabaseHelper;
    String newHouseNo;
    String newPostcode;
    String newAddress;
    String newTown;
    String newRent;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_properties);

        getSupportActionBar().setTitle("All Properties");
        mListView = (ListView) findViewById(R.id.list_properties_view);
        mDatabaseHelper = new DatabaseHelper(this);

        populateListView();
    }

    public void populateListView(){

        Cursor allData = mDatabaseHelper.getData();
        Log.d(TAG, "populateListView: Displaying data in the ListView." + allData.toString());

        ArrayList<String> listData = new ArrayList<>();

        while(allData.moveToNext()){

            //get the data from the db and add it to the list
            listData.add(allData.getString(1));
//               listData.add(allData.getString(2));
//            listData.add(data.getString(3));
//            listData.add(data.getString(4));

        }

        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String houseNo = adapterView.getItemAtPosition(i).toString();

                //get the id associated to that no.
                Cursor houseItemCursor = mDatabaseHelper.getHouseItemID(houseNo);

                Intent getIntent = getIntent();
                newPostcode = getIntent.getStringExtra("postcode");
                newAddress = getIntent.getStringExtra("address");
                newTown = getIntent.getStringExtra("town");
                newRent = getIntent.getStringExtra("rent");

                //todo - pass in the right values using the 'get' value method in the db..
                //error handling, check if data is returned
                while (houseItemCursor.moveToNext()){
                   int houseItemCursorInt = houseItemCursor.getInt(0);

                    if (houseItemCursorInt > -1 ){
                        //if a number is successfully returned
                        Log.d(TAG, "item id is ... " + houseItemCursorInt);
                        Intent editDataIntent = new Intent(AllProperties.this, EditPropertyActivity.class);
                        editDataIntent.putExtra("id", houseItemCursorInt);
                        editDataIntent.putExtra("number", houseNo);
                        editDataIntent.putExtra("postcode",newPostcode);
                        editDataIntent.putExtra("address",newAddress);
                        editDataIntent.putExtra("town",newTown);
                      //  editDataIntent.putExtra("postcode", postCode);
                        startActivity(editDataIntent);

                    }else{
                        Toast.makeText(AllProperties.this, "No ID associated with that name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
