package com.example.orderup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database version
    private static final int DATABSE_VERSION =1;

    //Database name
    private static final String DATABASE_NAME = "users.db";

    //Table Name
    private static final String TABLE_NAME = "users";

    //Table fields
    private static final String COLUMNN_ID = "id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_EMAIL = "Email";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String COLUMN_TYPE = "User_type";

    SQLiteDatabase database;

    //Methods for creation of the database
    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMNN_ID + " AUTO_INCREMENT PRIMARY KEY, " + COLUMN_NAME+" TEXT," + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT," + COLUMN_TYPE + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_NAME);
        onCreate(db);
    }
    // Method for adding recorded data to the database
    public boolean addData(String name, String email, String password, int id){
        database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_TYPE, id);

        Log.d(DATABASE_NAME, "AddData: Adding "+ name + " " + email + " " +  password + " " + id + " to " + DATABASE_NAME);

        long result = database.insert(TABLE_NAME, null, contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }
}
