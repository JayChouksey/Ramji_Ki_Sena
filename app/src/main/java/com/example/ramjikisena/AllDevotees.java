package com.example.ramjikisena;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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
import com.android.volley.toolbox.Volley;
import com.example.ramjikisena.recyclerfiles.Devotee;
import com.example.ramjikisena.recyclerfiles.DevoteeAdapter;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllDevotees extends AppCompatActivity implements DevoteeAdapter.OnRecyclerViewClickListener {

    RecyclerView recyclerView;
    private int lastClickedPosition = RecyclerView.NO_POSITION; // color
    DevoteeAdapter adapter;

    String urlAllDevotees = "https://restapiramji.onrender.com/user/allDevotees";

    // Get the authorization token passed from the previous activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_devotees);

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
                        Intent intent = new Intent(AllDevotees.this, ImportantTemples.class);
                        startActivity(intent);
                    }
                });

                // To go from All Devotees to Mission
                TextView mission = findViewById(R.id.ourMission);
                mission.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start the second activity
                        Intent intent = new Intent(AllDevotees.this, Mission.class);
                        startActivity(intent);
                    }
                });

                // To go from All Devotees to Glory of Ram Naam
                TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
                ramNaamGlory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start the second activity
                        Intent intent = new Intent(AllDevotees.this, GloryOfRamnaam.class);
                        startActivity(intent);
                    }
                });

                // To go from All Devotees to Writing Experience
                TextView write = findViewById(R.id.writingExperience);
                write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Start the second activity
                        Intent intent = new Intent(AllDevotees.this, WritingExperience.class);
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

        // Putting devotee data into table



        // color change code on click
        recyclerView = findViewById(R.id.all_devotees_recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));// Initialize Adapter with an empty list
        adapter = new DevoteeAdapter(new ArrayList<>());

        fetchDevoteeData();

    }
    // End of main function

    private void fetchDevoteeData() {
        // Get the authorization token passed from the previous activity
        Intent intent = getIntent();
        String token = intent.getStringExtra("token");

        String url = "https://restapiramji.onrender.com/user/allDevotees";
        List<Devotee> devoteeList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AllDevotees.this, "Data Fetched Successfully", Toast.LENGTH_SHORT).show();

                        try {
                            JSONArray allUsersArray = response.getJSONArray("allUsers");

                            for (int i = 0; i < allUsersArray.length(); i++) {
                                JSONObject userObject = allUsersArray.getJSONObject(i);
                                int rankInt = userObject.getInt("rank");
                                String rank = Integer.toString(rankInt);
                                String name = userObject.has("name") ? userObject.getString("name") : "Unknown";
                                int todaysCountInt = 0;
                                if (userObject.has("dailyCounts")) {
                                    JSONArray dailyCountsArray = userObject.getJSONArray("dailyCounts");
                                    if (dailyCountsArray.length() > 0) {
                                        JSONObject dailyCountObject = dailyCountsArray.getJSONObject(dailyCountsArray.length() - 1);
                                        todaysCountInt = dailyCountObject.getInt("count");
                                    }
                                }
                                String todaysCount = Integer.toString(todaysCountInt);
                                String totalCount = Integer.toString(userObject.getInt("totalCount"));

                                Devotee devoteeData = new Devotee(rank, name, todaysCount, totalCount);
                                devoteeList.add(devoteeData);

                            }

                            // Update adapter with the fetched data
                            updateRecyclerView(devoteeList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AllDevotees.this, "Error in data fetching", Toast.LENGTH_SHORT).show();
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

    private void updateRecyclerView(List<Devotee> devoteeList) {

        // Sort the devoteeList based on rank
        Collections.sort(devoteeList, new Comparator<Devotee>() {
            @Override
            public int compare(Devotee devotee1, Devotee devotee2) {
                // Assuming rank is a String, you may need to parse it to an int for numerical comparison
                return Integer.parseInt(devotee1.getRank()) - Integer.parseInt(devotee2.getRank());
            }
        });

        recyclerView = findViewById(R.id.all_devotees_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(AllDevotees.this));
        DevoteeAdapter adapter = new DevoteeAdapter(devoteeList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Notify adapter of dataset changes
    }

    @Override
    public void onItemClick(int position) {
        // Reset background color of previously clicked item
        if (lastClickedPosition != RecyclerView.NO_POSITION) {
            View previousView = recyclerView.getChildAt(lastClickedPosition);
            previousView.setBackgroundColor(Color.TRANSPARENT);
        }

        // Change background color of clicked item
        View clickedView = recyclerView.getChildAt(position);
        clickedView.setBackgroundColor(ContextCompat.getColor(this, R.color.red));

        // Update last clicked position
        lastClickedPosition = position;
    }
}