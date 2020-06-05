package com.example.orderup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    SQLiteDatabase database;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COLUMNN_ID + " AUTO_INCREMENT PRIMARY KEY, " + COLUMN_NAME+" TEXT," + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + "TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_NAME);
        onCreate(db);
    }
}
