package com.example.restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView addressTextView;
    private TextView phoneTextView;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // initialize the TextViews
        nameTextView = findViewById(R.id.nameTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);

        // get the restaurant name from the intent
        Intent intent = getIntent();
        String restaurantName = intent.getStringExtra("name");

        // create the URL for the API call
        String url = "https://restarauntapica2.azurewebsites.net/api/Restaurant?name=" + restaurantName;

        // create the request queue
        requestQueue = Volley.newRequestQueue(this);

        // create the JSON object request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // get the restaurant data from the JSON object
                            String name = response.getString("name");
                            String address = response.getString("address");
                            String phone = response.getString("phone");

                            // display the restaurant data on the activity
                            nameTextView.setText(name);
                            addressTextView.setText(address);
                            phoneTextView.setText(phone);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        // add the JSON object request to the request queue
        requestQueue.add(jsonObjectRequest);
    }
}
