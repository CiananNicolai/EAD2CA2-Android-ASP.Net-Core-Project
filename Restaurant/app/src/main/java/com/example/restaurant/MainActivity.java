package com.example.restaurant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Restaurant> restaurantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // add some restaurants to the list
        restaurantList.add(new Restaurant("Restaurant A", "123 Main St", "555-1234"));
        restaurantList.add(new Restaurant("Restaurant B", "456 Elm St", "555-5678"));
        restaurantList.add(new Restaurant("Restaurant C", "789 Oak St", "555-9012"));

        // create the adapter and set it on the ListView
        RestaurantAdapter adapter = new RestaurantAdapter(this, restaurantList);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        // set an onItemClick listener on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = restaurantList.get(position);

                Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("name", restaurant.getName());
                intent.putExtra("address", restaurant.getAddress());
                intent.putExtra("phone", restaurant.getPhoneNumber());
                startActivity(intent);
            }
        });
    }


    public class RestaurantAdapter extends ArrayAdapter<Restaurant> {

        public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurants) {
            super(context, 0, restaurants);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Restaurant restaurant = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.restaurant_item, parent, false);
            }

            TextView nameTextView = convertView.findViewById(R.id.nameTextView);
            TextView addressTextView = convertView.findViewById(R.id.addressTextView);
            TextView phoneTextView = convertView.findViewById(R.id.phoneTextView);

            nameTextView.setText(restaurant.getName());
            addressTextView.setText(restaurant.getAddress());
            phoneTextView.setText(restaurant.getPhoneNumber());

            return convertView;
        }
    }

}