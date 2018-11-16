package com.example.muhammadwaqqas.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ListView l1;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        l1 = (ListView) findViewById(R.id.listView);
        loginDataBaseAdapter = new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        ArrayList<String> list1 = loginDataBaseAdapter.getMultipleEntryLoc();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list1);
        l1.setAdapter(arrayAdapter);

    }
}
