package com.example.muhammadwaqqas.api;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

public class ViewRestaurantInfo extends AppCompatActivity {

    TextView t1,t2;
    String restaurantName;
    String city;
    ProgressBar pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant_info);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        pBar.setVisibility(View.VISIBLE);
        t1 = (TextView) findViewById(R.id.txt1);
        t2 = (TextView) findViewById(R.id.txt2);
        String uri= "https://api.opencagedata.com/geocode/v1/json?key=de55bc793d97461d866b4c212bd9d26a&q=country:pakistan&pretty=1&no_annotations=1";
        Ion.with(this)
                .load(uri)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject data) {
                        JsonArray jsonArray = data.getAsJsonArray("results");
                        for (int i = 0; i < jsonArray.size() - 1; i++)
                        {
                            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                            JsonObject component = jsonObject.getAsJsonObject("components");
                            String type= component.get("_type").toString();
                            String ntype= type.substring(1,type.length()-1);

                            String cityLoc= component.get("city").toString();
                            String ncityLoc= cityLoc.substring(1,cityLoc.length()-1);
                            restaurantName= getIntent().getStringExtra("restaurant").toLowerCase();
                            city= getIntent().getStringExtra("cityName");

                            if(ntype.equals("restaurant"))
                            {
                                String resName= component.get("restaurant").toString().toLowerCase();
                                if(resName.contains(restaurantName) && city.equals(ncityLoc)) {
                                    String address = String.valueOf(jsonObject.get("formatted"));
                                    JsonObject bound = jsonObject.getAsJsonObject("bounds");
                                    JsonObject north = bound.getAsJsonObject("northeast");
                                    String latitude= north.get("lat").toString();
                                    String longitude= north.get("lng").toString();
                                    pBar.setVisibility(View.INVISIBLE);
                                    t1.setText("Address: " + address);
                                    t2.setText(latitude + ", " + longitude);
                                }
                            }
                            else
                            {
                                pBar.setVisibility(View.INVISIBLE);
                                t1.setText("No restaurants by this name");
                            }
                        }
                    }
                });
    }
}
