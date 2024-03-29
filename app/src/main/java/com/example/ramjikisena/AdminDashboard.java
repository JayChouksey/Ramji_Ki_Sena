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
import com.android.volley.toolbox.Volley;
import com.example.ramjikisena.recyclerfiles.Devotee;
import com.example.ramjikisena.recyclerfiles.DevoteeAdapter;
import com.example.ramjikisena.recyclerfiles.History;
import com.example.ramjikisena.recyclerfiles.HistoryAdapter;
import com.example.ramjikisena.recyclerfiles.UserAdapter;
import com.example.ramjikisena.recyclerfiles.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        // Download user info
        /*{
            Button download = findViewById(R.id.buttonDownloadUserInfo);
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }*/
        //  End of Download user info

        // Text movement code
        {
            // To move text in the screen
            TextView movingText = findViewById(R.id.movingText);

            // Define the animation
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            Animation animation = new TranslateAnimation(screenWidth, -screenWidth, 0, 0); // Change 1000 to the width of your screen
            animation.setDuration(18000); // Set duration in milliseconds
            animation.setRepeatMode(Animation.RESTART);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setInterpolator(new LinearInterpolator()); // Use linear interpolator for smooth movement
            // Start the animation
            movingText.startAnimation(animation);


            // To move the brand in the screen
            ImageView imageView = findViewById(R.id.sponsorLogo);

            int screenWidth2 = getResources().getDisplayMetrics().widthPixels;
            Animation animation2 = new TranslateAnimation(screenWidth2, -screenWidth2, 0, 0); // Change 1000 to the width of your screen
            animation.setDuration(18000); // Set duration in milliseconds
            animation.setRepeatMode(Animation.RESTART);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setInterpolator(new LinearInterpolator()); // Use linear interpolator for smooth movement

            // Start the animation
            imageView.startAnimation(animation);
        }
        //End of Text movement code
        // ----------------------------------------------------------------------------------------------------------

        // Switching from one activity to another code
        {
            // To go from AdminDashboard to Register
            Button buttonCreateUser = findViewById(R.id.buttonCreateUser);
            buttonCreateUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(AdminDashboard.this, Register.class);
                    startActivity(intent);
                }
            });

            // To go from AdminDashboard to Register
            Button buttonLoginUser = findViewById(R.id.buttonLoginUser);
            buttonLoginUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            // To go from AdminDashboard to MainActivity
            Button buttonLogOut = findViewById(R.id.buttonLogOut);
            buttonLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Toast.makeText(AdminDashboard.this, "Logout done", Toast.LENGTH_SHORT).show();
                    logout();
                }
            });

            // To go from AdminDashboard to ViewUsers
            Button buttonViewUsers = findViewById(R.id.buttonViewUsers);
            buttonViewUsers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the authorization token passed from the previous activity
                    Intent intent = getIntent();
                    String token = intent.getStringExtra("token");

                    // Start the second activity
                    Intent intentC = new Intent(AdminDashboard.this, ViewUsers.class);
                    intentC.putExtra("token", token);
                    startActivity(intentC);
                }
            });

        }

        // End Switching from one activity to another code

        // --------------------------------------------------------------------------------------------------------------

        // Collecting dashboard data from API
        {

            // Get the authorization token passed from the previous activity
            Intent intent = getIntent();
            String token = intent.getStringExtra("token");

            // Initialize textviews
            TextView totalUserTextView = findViewById(R.id.TextViewTotalUser);
            TextView totalRamCountTextView = findViewById(R.id.TextViewRamCount);

            // Make a GET request to the profile endpoint
            String apiDashboard = "https://restapiramji.onrender.com/admin/dashboard"; // API endpoint

            RequestQueue queue = Volley.newRequestQueue(this);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiDashboard, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int userCount = response.getInt("userCount");
                                int totalRamCount = response.getInt("totalRamnaamCount");

                                // Set data to TextViews
                                totalUserTextView.setText(String.valueOf(userCount));
                                totalRamCountTextView.setText(String.valueOf(totalRamCount));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminDashboard.this, "Error in getting data", Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("authorization", token);
                    return headers;
                }
            };

            queue.add(jsonObjectRequest);
        }

        // Collecting dashboard data from API

        // ------------------------------------------------------------------------------------------------------------

    }

    //--------------------------------------- Log out function for API call --------------------------------------------------
    private void logout() {
        String urlLogOut = "https://restapiramji.onrender.com/auth/logout"; // API endpoint for logout
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlLogOut, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if success is true
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // Logout successful, navigate to MainActivity
                                Intent intent = new Intent(AdminDashboard.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Close the current activity
                            } else {
                                // Logout failed, show error message
                                Toast.makeText(AdminDashboard.this, "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            Toast.makeText(AdminDashboard.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(AdminDashboard.this, "Logout failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
    //--------------------------------------- Log out function for API call --------------------------------------------------

    @Override
    public void onBackPressed() {
        // Do nothing (disable back button)

    }

}
// End of Class