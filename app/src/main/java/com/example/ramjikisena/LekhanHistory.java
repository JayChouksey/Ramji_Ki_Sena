package com.example.ramjikisena;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ramjikisena.recyclerfiles.Devotee;
import com.example.ramjikisena.recyclerfiles.DevoteeAdapter;
import com.example.ramjikisena.recyclerfiles.History;
import com.example.ramjikisena.recyclerfiles.HistoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LekhanHistory extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lekhan_history);

        // Text movement code
        {
            // To move text in the screen
            TextView movingText = findViewById(R.id.movingText);
            TextView movingTextBottom = findViewById(R.id.movingTextBottom);
            // Define the animation
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            Animation animation = new TranslateAnimation(screenWidth, -screenWidth, 0, 0); // Change 1000 to the width of your screen
            animation.setDuration(18000); // Set duration in milliseconds
            animation.setRepeatMode(Animation.RESTART);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setInterpolator(new LinearInterpolator()); // Use linear interpolator for smooth movement
            // Start the animation
            movingText.startAnimation(animation);
            movingTextBottom.startAnimation(animation);

            // To move the brand in the screen
            ImageView imageView = findViewById(R.id.sponsorLogo);
            ImageView imageViewBottom = findViewById(R.id.sponsorLogoBottom);

            int screenWidth2 = getResources().getDisplayMetrics().widthPixels;
            Animation animation2 = new TranslateAnimation(screenWidth2, -screenWidth2, 0, 0); // Change 1000 to the width of your screen
            animation.setDuration(18000); // Set duration in milliseconds
            animation.setRepeatMode(Animation.RESTART);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setInterpolator(new LinearInterpolator()); // Use linear interpolator for smooth movement

            // Start the animation
            imageView.startAnimation(animation);
            imageViewBottom.startAnimation(animation);
        }
        //End of Text movement code
        // ----------------------------------------------------------------------------------------------------------


        // Switching from one activity to another code
        {
            // To go from All Devotees to Important Temples
            TextView temples = findViewById(R.id.ImpTemples);
            temples.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(LekhanHistory.this, ImportantTemples.class);
                    startActivity(intent);
                }
            });

            // To go from All Devotees to Mission
            TextView mission = findViewById(R.id.ourMission);
            mission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(LekhanHistory.this, Mission.class);
                    startActivity(intent);
                }
            });

            // To go from All Devotees to Glory of Ram Naam
            TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
            ramNaamGlory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(LekhanHistory.this, GloryOfRamnaam.class);
                    startActivity(intent);
                }
            });

            // To go from All Devotees to Writing Experience
            TextView write = findViewById(R.id.writingExperience);
            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(LekhanHistory.this, WritingExperience.class);
                    startActivity(intent);
                }
            });

            // To go from All Devotees to Writing Experience
            Button home = findViewById(R.id.HomeButton);
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                   onBackPressed();
                }
            });



            // End of Switch Activity
        }
        //----------------------------------------------------------------------------------------------------------

        // Putting history data into table
/*        recyclerView = findViewById(R.id.lekhan_history_recycle);
        List<History> historyList = new ArrayList<>();

        historyList.add(new History("02/05/2023","253"));
        historyList.add(new History("02/05/2023","253"));
        historyList.add(new History("02/05/2023","253"));
        historyList.add(new History("02/05/2023","253"));
        historyList.add(new History("02/05/2023","253"));

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(adapter);*/

        fetchUserData();
    }


    private void fetchUserData() {
        // Get the authorization token passed from the previous activity
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        String url = "https://restapiramji.onrender.com/user/lekhanHistory";
        List<History> historyList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(LekhanHistory.this, "Data Fetched Successfully", Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject userObject = response.getJSONObject("user");
                            JSONArray dailyCountsArray = userObject.getJSONArray("dailyCounts");

                            for (int i = dailyCountsArray.length()-1; i >= 0 ; i--) {
                                JSONObject dailyCountObject = dailyCountsArray.getJSONObject(i);
                                String date = dailyCountObject.getString("date");
                                date = date.substring(0,10);
                                int countInt = dailyCountObject.getInt("count");
                                String count = Integer.toString(countInt);
                                History userData = new History(date, count);
                                historyList.add(userData);
                            }

                            // Update adapter with the fetched data
                            updateRecyclerView(historyList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LekhanHistory.this, "Error in data fetching", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<>();
                header.put("authorization", token);
                return header;
            }
        };

        // Add the request to the RequestQueue
        queue.add(jsonObjectRequest);
    }

    private void updateRecyclerView(List<History> historyList) {
        recyclerView = findViewById(R.id.lekhan_history_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(LekhanHistory.this));
        adapter = new HistoryAdapter(historyList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Notify adapter of dataset changes
    }

}