package com.iuh.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDBConnection();
    }

    public void getDBConnection() {
        ListView listNames = (ListView) findViewById(R.id.listView);
        SQLiteOpenHelper userDatabaseHelper = new UserDatabaseHelper(this);
        try {
            db = userDatabaseHelper.getReadableDatabase();
            cursor = db.query("USER",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, new String[]{"NAME"},
                    new int[]{android.R.id.text1}, 0);
            listNames.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void addUser(View view) {
        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
        EditText name = (EditText) findViewById(R.id.etName);
        db = userDatabaseHelper.getWritableDatabase();
        userDatabaseHelper.insertUser(db, String.valueOf(name.getText()));
        getDBConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}