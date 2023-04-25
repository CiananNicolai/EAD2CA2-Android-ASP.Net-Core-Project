package com.example.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class RestaurantDetailsActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView typeTextView;
    private TextView addressTextView;
    private TextView phoneTextView;
    private TextView descriptionTextView;
    private TextView starsTextView;
    private TextView pictureUrlTextView;
    private ImageView restaurantImageView;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // Enable back button in action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initialize the TextViews and ImageView
        nameTextView = findViewById(R.id.nameTextView);
        typeTextView = findViewById(R.id.typeTextView);
        addressTextView = findViewById(R.id.addressTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        starsTextView = findViewById(R.id.starsTextView);
        pictureUrlTextView = findViewById(R.id.pictureUrlTextView);
        restaurantImageView = findViewById(R.id.restaurantImageView);

        // get the restaurant id from the intent
        Intent intent = getIntent();
        int restaurantId = intent.getIntExtra("id", -1);

        // create the URL for the API call
        String url = "https://restarauntapica2.azurewebsites.net/api/Restaurant/" + restaurantId;
        Log.d("RestaurantDetailsActivity", "restaurantId lol: " + restaurantId);

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
                            String type = response.getString("type");
                            String address = response.getString("address");
                            String phone = response.getString("phone");
                            String description = response.getString("description");
                            float stars = (float) response.getDouble("stars");

                            // display the restaurant data on the activity
                            nameTextView.setText(name);
                            typeTextView.setText(type);
                            addressTextView.setText(address);
                            phoneTextView.setText(phone);
                            descriptionTextView.setText(description);
                            starsTextView.setText(String.valueOf(stars));
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

    // Handle back button press in action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
