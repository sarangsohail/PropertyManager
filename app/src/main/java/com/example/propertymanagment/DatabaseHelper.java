package com.example.propertymanagment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.PipedOutputStream;

class DatabaseHelper extends SQLiteOpenHelper{

    public static final String TAG = "database.helper.";

    public static final String TABLE_NAME = "prop_table";
    public static final String COL0  = "ID";
    public static final String COL1 = "name"; //houseNumber
    public static final String COL2 = "postcode";
    public static final String COL3 = "address";
    public static final String COL4 = "town";
    public static final String COL5 = "rent";
//    public static final String COL6 = "name";
//    public static final String COL7 = "name";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null,8 );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COL1 + " TEXT)";
//        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COL1 + ",  TEXT" +  COL2 +  ", TEXT" + COL3 + ", TEXT" + COL4  + ", TEXT)" ;

        String createTable = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + "("
                + COL0 + " INTEGER PRIMARY KEY," // and auto increment will be handled with                            primary key
                + COL1 + " TEXT NOT NULL,"
                + COL2 + " TEXT NOT NULL,"
                + COL3 + " TEXT NOT NULL,"
                + COL4 + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addData(String number, String postcode, String address, String town){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, number);
        contentValues.put(COL2, postcode);
        contentValues.put(COL3, address);
        contentValues.put(COL4, town);

        //see if data was inserted properly
        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Log.d(TAG, "addDATA : adding " +  number + "to " + TABLE_NAME);

        if (result != -1 ){
            return false;

        }else {
            return true;
        }
    }

     public Cursor getHouseItemID(String houseNo ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                 " WHERE " + COL1 + " = '" + houseNo + "'";
        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
     }

    public Cursor getPostcodeItemID(String postCode ){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + postCode + "'";

        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }

    public Cursor getAddress(String address ){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL3 + " = '" + address + "'";

        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }
    public Cursor getTown(String town ){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL4 + " = '" + town + "'";

        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }
    public Cursor getRent(String rent ){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String query = "SELECT " + COL0 + " FROM " + TABLE_NAME +
                " WHERE " + COL5 + " = '" + rent + "'";


        Cursor data = sqLiteDatabase.rawQuery(query, null);
        return data;
    }

    //returns all data from db
    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        Log.d(TAG, data.toString());
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

    public void updatePostcode(String newPostcode, int id, String oldPostcode){
        SQLiteDatabase databaseHelper = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newPostcode + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldPostcode + "'";

        databaseHelper.execSQL(query);
    }

    public void updateAddress(String newAddress, int id, String oldAddress){
        SQLiteDatabase databaseHelper = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newAddress + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldAddress + "'";

        databaseHelper.execSQL(query);
    }


    public void updateTown(String newTown, int id, String oldTown){
        SQLiteDatabase databaseHelper = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newTown + "' WHERE " + COL0 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldTown + "'";

        databaseHelper.execSQL(query);
    }

//    public void updateRent(String newRent, int id, String oldRent){
//        SQLiteDatabase databaseHelper = this.getWritableDatabase();
//        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
//                " = '" + newRent + "' WHERE " + COL0 + " = '" + id + "'" +
//                " AND " + COL5 + " = '" + oldRent + "'";
//
//        databaseHelper.execSQL(query);
//    }


    public void deleteNumber(int id, String number){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL0 + " = '" + id + "'" +
                " AND " + COL1 + " = '" + number + "'";

        sqLiteDatabase.execSQL(query);

    }

    //todo - maybe use this later
//    public void deleteTable(){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS prop_table");
//
//    }
}
