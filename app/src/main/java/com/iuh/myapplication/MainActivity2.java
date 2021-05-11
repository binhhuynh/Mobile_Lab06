package com.iuh.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private List<Location> locations;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        readDataFromDB(recyclerView);
    }

    public void readDataFromDB(RecyclerView recyclerView) {
        SQLiteOpenHelper travelDatabaseHelper = new TravelDatabaseHelper(this);
        locations = new ArrayList<>();
        try {
            db = travelDatabaseHelper.getReadableDatabase();
            cursor = db.query("LOCATION",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);
            while(cursor.moveToNext()) {
                Location location = new Location(cursor.getInt(0), cursor.getString(1));
                locations.add(location);
            }

            customAdapter = new CustomAdapter(locations);

            recyclerView.setAdapter(customAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void addLocation(View view) {
        TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(this);
        EditText location = (EditText) findViewById(R.id.etName);
        db = travelDatabaseHelper.getWritableDatabase();
        long result = travelDatabaseHelper.insertLocation(db, String.valueOf(location.getText()));
        if (result != -1) {
            Toast toast = Toast.makeText(this, "Inserted", Toast.LENGTH_SHORT);
            toast.show();
            readDataFromDB(recyclerView);
            location.getText().clear();
        } else {
            Toast toast = Toast.makeText(this, "Not inserted", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void cancelLocation(View view) {
        EditText location = (EditText) findViewById(R.id.etName);
        location.getText().clear();
    }
}