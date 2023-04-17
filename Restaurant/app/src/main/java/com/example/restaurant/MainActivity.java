package com.example.restaurant;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.restaurant.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


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