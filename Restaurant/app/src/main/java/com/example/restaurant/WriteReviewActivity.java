package com.example.restaurant;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WriteReviewActivity extends AppCompatActivity {

    private static final String TAG = "WriteReviewActivity";
    private EditText mAuthorEditText;
    private EditText mBodyEditText;
    private RatingBar mRatingBar;
    private Button mSubmitButton;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int restaurantId = getIntent().getIntExtra("id", -1);
        if (restaurantId == -1) {
            Log.e(TAG, "No restaurant ID provided");
            Toast.makeText(this, "Error: no restaurant ID provided", Toast.LENGTH_SHORT).show();
            finish();
        }

        mAuthorEditText = findViewById(R.id.edit_text_author);
        mBodyEditText = findViewById(R.id.edit_text_body);
        mRatingBar = findViewById(R.id.rating_bar);
        mSubmitButton = findViewById(R.id.submit_button);

        mRequestQueue = Volley.newRequestQueue(this);

        mSubmitButton.setOnClickListener(view -> {
            String author = mAuthorEditText.getText().toString().trim();
            String body = mBodyEditText.getText().toString().trim();
            int rating = (int) mRatingBar.getRating();

            if (author.isEmpty() || body.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject requestBody = new JSONObject();
            try {
                requestBody.put("author", author);
                requestBody.put("body", body);
                requestBody.put("rating", rating);
            } catch (JSONException e) {
                Log.e(TAG, "Error creating JSON request body", e);
                Toast.makeText(this, "Error: unable to create review", Toast.LENGTH_SHORT).show();
                return;
            }

            String url = "https://restarauntapica2.azurewebsites.net/api/Restaurant/" + restaurantId + "/reviews";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "Review submitted: " + response.toString());
                            Toast.makeText(WriteReviewActivity.this, "Review submitted", Toast.LENGTH_SHORT).show();
                            // Navigate back to restaurant details activity
                            Intent intent = new Intent(WriteReviewActivity.this, RestaurantDetailsActivity.class);
                            intent.putExtra("id", restaurantId);
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG, "Error submitting review", error);
                            Toast.makeText(WriteReviewActivity.this, "Review submitted", Toast.LENGTH_SHORT).show();
                            // Navigate back to restaurant details activity
                            Intent intent = new Intent(WriteReviewActivity.this, RestaurantDetailsActivity.class);
                            intent.putExtra("id", restaurantId);
                            startActivity(intent);
                            finish();
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    return headers;
                }
            };

            mRequestQueue.add(request);
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
