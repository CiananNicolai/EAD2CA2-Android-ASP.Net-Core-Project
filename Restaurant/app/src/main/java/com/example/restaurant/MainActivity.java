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
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Restaurant> restaurantList = new ArrayList<>();
    private RestaurantAdapter adapter;
    private Spinner sortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to the views
        ListView listView = findViewById(R.id.listView);
        sortSpinner = findViewById(R.id.sortSpinner);

        // create the adapter and set it on the ListView
        adapter = new RestaurantAdapter(this, restaurantList);
        listView.setAdapter(adapter);

        // set an onItemClick listener on the ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant restaurant = restaurantList.get(position);

                Intent intent = new Intent(MainActivity.this, RestaurantDetailsActivity.class);
                intent.putExtra("id", restaurant.getId());
                startActivity(intent);
            }
        });

        // set an onItemSelectedListener on the Spinner
        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = sortSpinner.getSelectedItem().toString();
                sortRestaurantsByType(selectedType.equals("All") ? null : selectedType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // populate the spinner with the restaurant types
        Spinner sortSpinner = findViewById(R.id.sortSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.restaurant_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(adapter);

        // set the default selected type to "All"
        sortSpinner.setSelection(adapter.getPosition("All"));

        // get restaurants from the API
        getRestaurantsFromAPI();

        // show unfiltered list
        sortRestaurantsByType(null);
    }

    private void getRestaurantsFromAPI() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://restarauntapica2.azurewebsites.net/api/Restaurant")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            int id = jsonObject.getInt("id");
                            String name = jsonObject.getString("name");
                            String address = jsonObject.getString("address");
                            String type = jsonObject.getString("type");

                            Restaurant restaurant = new Restaurant(id, name, address, type);
                            restaurantList.add(restaurant);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // notify the adapter that the data has changed
                                adapter.notifyDataSetChanged();

                                // populate the list with the new data
                                sortRestaurantsByType(null);
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void sortRestaurantsByType(String selectedType) {
        // check if restaurantList is empty, and return if it is
        if (restaurantList.isEmpty()) {
            return;
        }

        ArrayList<Restaurant> filteredList = new ArrayList<>();
        if (selectedType == null) {
            filteredList.addAll(restaurantList);
        } else {
            for (Restaurant restaurant : restaurantList) {
                if (restaurant.getType().equals(selectedType)) {
                    filteredList.add(restaurant);
                }
            }
        }
        adapter = new RestaurantAdapter(this, filteredList);
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
            TextView typeTextView = convertView.findViewById(R.id.typeTextView);

            nameTextView.setText(restaurant.getName());
            addressTextView.setText(restaurant.getAddress());
            typeTextView.setText(restaurant.getType());

            return convertView;
        }
    }
}