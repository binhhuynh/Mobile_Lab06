package com.iuh.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class UserDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user";
    private static final int DB_VERSION = 1;

    UserDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE USER (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME TEXT);");
    }

    public long insertUser(SQLiteDatabase db, String name) {
        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        long rowID = db.insert("USER", null, userValues);
        return rowID;
    }

    public long deleteUser(SQLiteDatabase db, String name) {
        ContentValues userValues = new ContentValues();
        userValues.put("NAME", name);
        long rowID = db.delete("USER", "NAME = ?", new String[] {name});
        return rowID;
    }
}
