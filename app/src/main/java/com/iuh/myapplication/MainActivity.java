package com.iuh.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity<itemClickListener> extends AppCompatActivity {
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
        long result = userDatabaseHelper.insertUser(db, String.valueOf(name.getText()));
        if (result != -1) {
            Toast toast = Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT);
            toast.show();
            getDBConnection();
            name.getText().clear();
        } else {
            Toast toast = Toast.makeText(this, "Not inserted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void deleteUser(View view) {
        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
        EditText name = (EditText) findViewById(R.id.etName);
        db = userDatabaseHelper.getWritableDatabase();
        long result = userDatabaseHelper.deleteUser(db, String.valueOf(name.getText()));
        if (result != 0) {
            Toast toast = Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT);
            toast.show();
            getDBConnection();
            name.getText().clear();
        } else {
            Toast toast = Toast.makeText(this, "Not deleted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void cancelUser(View view) {
        UserDatabaseHelper userDatabaseHelper = new UserDatabaseHelper(this);
        EditText name = (EditText) findViewById(R.id.etName);
        name.getText().clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}