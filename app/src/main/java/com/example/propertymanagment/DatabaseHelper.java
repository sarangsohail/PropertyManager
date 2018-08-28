package com.example.propertymanagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper{

    public static final String TAG = "DatabaseHelper";

    public static final String TABLE_NAME = "prop_table";
    public static final String COL0  = "ID";
    public static final String COL1 = "name";


    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL1 + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String item){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, item);

        //see if data was inserted properly
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        if (result == -1 ){
            return false;
        }else {
            return true;
        }
    }

    //returns all data from db
    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;

    }

     public Cursor getItemId(String houseNo){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                 " WHERE " + COL1 + " = '" + houseNo + "'";

        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
     }


     //update values for the house number
    public void updateNumber(String newNumber, int id, String oldNumber){
        SQLiteDatabase databaseHelper = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL1 +
                " = '" + newNumber + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + oldNumber + "'";

        databaseHelper.execSQL(query);
    }

    public void deleteNumber(int id, String number){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + number + "'";

        sqLiteDatabase.execSQL(query);

    }

}
