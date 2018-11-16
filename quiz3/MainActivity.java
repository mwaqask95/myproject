package com.example.muhammadwaqqas.api;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.load;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText restaurantName;
    Spinner spinner;
    String city;
    String receieveOk;
    LoginDataBaseAdapter loginDataBaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinnerCountry);
        spinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Karachi");
        categories.add("Islamabad");
        categories.add("Rawalpindi");
        categories.add("Sukkur");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        city = parent.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void find(View view) {
        restaurantName = (EditText) findViewById(R.id.resText);
        String restaurantName1 = restaurantName.getText().toString();
        loginDataBaseAdapter=new LoginDataBaseAdapter(getApplicationContext());
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        receieveOk=loginDataBaseAdapter.insertEntry(restaurantName1);

        Intent intent = new Intent(getBaseContext(),ViewRestaurantInfo.class );
        intent.putExtra("cityName",city);
        intent.putExtra("restaurant", restaurantName1);
        startActivity(intent);
    }

    public void search(View view) {
        Intent intent = new Intent(getBaseContext(),SearchActivity.class);
        startActivity(intent);
    }
}
