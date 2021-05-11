package com.iuh.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TravelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "travel";
    private static final int DB_VERSION = 1;

    TravelDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db);
    }

    private void updateMyDatabase(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LOCATION (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT);");
    }

    public long insertLocation(SQLiteDatabase db, String name) {
        ContentValues locationValues = new ContentValues();
        locationValues.put("NAME", name);
        return db.insert("LOCATION", null, locationValues);
    }

    public int deleteLocation(SQLiteDatabase db, String name) {
        ContentValues locationValues = new ContentValues();
        locationValues.put("NAME", name);
        return db.delete("LOCATION", "NAME = ?", new String[] {name});
    }
}
