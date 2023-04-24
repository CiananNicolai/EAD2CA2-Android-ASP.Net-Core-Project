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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Restaurant> restaurantList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get restaurants from the API
        getRestaurantsFromAPI();

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
                intent.putExtra("id", restaurant.getId());
                intent.putExtra("name", restaurant.getName());
                intent.putExtra("address", restaurant.getAddress());
                intent.putExtra("phone", restaurant.getPhone());
                startActivity(intent);
            }
        });
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
                            String phone = jsonObject.getString("phone");

                            Restaurant restaurant = new Restaurant(id, name, address, phone);
                            restaurantList.add(restaurant);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // notify the adapter that the data has changed
                                ((ArrayAdapter) ((ListView) findViewById(R.id.listView)).getAdapter()).notifyDataSetChanged();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
            phoneTextView.setText(restaurant.getPhone());

            return convertView;
        }
    }
}
