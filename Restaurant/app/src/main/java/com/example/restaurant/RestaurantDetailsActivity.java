package com.example.restaurant;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // enable the back button in the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // get the restaurant details from the intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String name = extras.getString("name");
            String address = extras.getString("address");
            String phone = extras.getString("phone");

            // update the text views with the restaurant details
            TextView nameTextView = findViewById(R.id.nameTextView);
            TextView addressTextView = findViewById(R.id.addressTextView);
            TextView phoneTextView = findViewById(R.id.phoneTextView);

            nameTextView.setText(name);
            addressTextView.setText(address);
            phoneTextView.setText(phone);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle back button clicks here
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
