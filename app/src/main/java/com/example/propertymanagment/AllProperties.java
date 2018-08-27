package com.example.propertymanagment;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AllProperties extends AppCompatActivity {

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

        //get the data from DB helper class
        Cursor data = mDatabaseHelper.getData();

        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()){
            //get the data from the db colum 1 and add it to list
            listData.add(data.getString(1));
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);
    }
}
