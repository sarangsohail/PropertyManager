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

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_properties);

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
//            listData.add(data.getString(2));
//            listData.add(data.getString(3));
//            listData.add(data.getString(4));

        }

        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String houseNo = adapterView.getItemAtPosition(i).toString();
                String postCode = adapterView.getItemAtPosition(i).toString();

                //get the id associated to that no.
                Cursor houseItemCursor = mDatabaseHelper.getHouseItemID(houseNo);
                Cursor postCodeItemCursor = mDatabaseHelper.getPostcodeItemID(postCode);

                //error handling, check if data is returned
                while (houseItemCursor.moveToNext()){
                   int itemID = houseItemCursor.getInt(0);

                    if (itemID > -1){
                        //if a number is successfully returned
                        Log.d(TAG, "item id is ... " + itemID);
                        Intent editDataIntent = new Intent(AllProperties.this, EditPropertyActivity.class);
                        editDataIntent.putExtra("id", itemID);
                        editDataIntent.putExtra("number", houseNo);
                        editDataIntent.putExtra("postcode", postCode);
                        startActivity(editDataIntent);

                    }else{
                        Toast.makeText(AllProperties.this, "No ID associated with that name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
