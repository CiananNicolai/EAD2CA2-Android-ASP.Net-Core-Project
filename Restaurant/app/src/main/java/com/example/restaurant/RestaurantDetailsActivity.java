package com.example.restaurant;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.android.volley.toolbox.JsonArrayRequest;
import org.json.JSONArray;
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
    private TextView reviewsTextView;
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
        reviewsTextView = findViewById(R.id.reviewsTextView);
        restaurantImageView = findViewById(R.id.restaurantImageView);

        // get the restaurant id from the intent
        Intent intent = getIntent();
        int restaurantId = intent.getIntExtra("id", -1);

        // create the URL for the API call
        String url = "https://restarauntapica2.azurewebsites.net/api/Restaurant/" + restaurantId;

        // create the request queue
        requestQueue = Volley.newRequestQueue(this);

        // create the JSON object request for restaurant details
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

        // create the JSON array request for restaurant reviews
        String reviewsUrl = "https://restarauntapica2.azurewebsites.net/api/Restaurant/" + restaurantId + "/reviews";

        // create the request queue
        requestQueue = Volley.newRequestQueue(this);

        // create the JSON object request for the restaurant data
        JsonObjectRequest restaurantJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, reviewsUrl, null,
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
                            String pictureUrl = response.getString("pictureUrl");

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

        // add the JSON object request for the restaurant data to the request queue
        requestQueue.add(restaurantJsonObjectRequest);



        // create the JSON array request for reviews
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, reviewsUrl, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // iterate through the reviews and append them to the reviewsTextView
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject review = response.getJSONObject(i);
                                String author = review.getString("author");
                                String body = review.getString("body");
                                float rating = (float) review.getDouble("rating");
                                reviewsTextView.append(author + " - " + body + " (" + rating + ")\n\n");
                            }
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

        // add the JSON array request for reviews to the request queue
        requestQueue.add(jsonArrayRequest);

        Button writeReviewButton = findViewById(R.id.write_review_button);
        writeReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetailsActivity.this, WriteReviewActivity.class);
                intent.putExtra("id", restaurantId);
                startActivity(intent);
            }
        });
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

