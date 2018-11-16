package com.example.muhammadwaqqas.api;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LoginDataBaseAdapter {
    static final String DATABASE_NAME = "database.db2";
    String ok = "OK";
    static final int DATABASE_VERSION = 1;
    public static String getResult = "";

    public static final int NAME_COLUMN = 1;
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    static final String DATABASE_CREATE = "create table LOGIN( ID integer primary key autoincrement,SEARCHLOC  text,DATE  text); ";


    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private static DataBaseHelper dbHelper;

    public LoginDataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method to open the Database
    public LoginDataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    // Method to close the Database
    public void close() {
        db.close();
    }

    // method returns an Instance of the Database
    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }

    // method to insert a record in Table
    @RequiresApi(api = Build.VERSION_CODES.O)
    public String insertEntry(String location) {

        try {

            ContentValues newValues = new ContentValues();
            newValues.put("SEARCHLOC", location);
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date()
            );
            newValues.put("DATE", timeStamp);

            db = dbHelper.getWritableDatabase();
            long result = db.insert("LOGIN", null, newValues);
            Toast.makeText(context, "Search Info Saved", Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            System.out.println("Exceptions " + ex);
            Log.e("Note", "One row entered");
        }
        return ok;
    }
    public ArrayList getMultipleEntryLoc()
    {
        ArrayList<String> list1= new ArrayList<>();
        db=dbHelper.getReadableDatabase();
        Cursor cursor=db.query("LOGIN", null, "ID>0",null, null, null, null);
        cursor.moveToFirst();
        if(cursor.getCount()>0)
        {
            do {
                list1.add(cursor.getString(cursor.getColumnIndex("SEARCHLOC"))+ "\n" + cursor.getString(cursor.getColumnIndex("DATE")));
            }while(cursor.moveToNext());
        }

        return list1;
    }

}
