package com.example.ramjikisena;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the user is already logged in
        if (isLoggedIn()) {
            // Navigate to ProfileActivity
            Intent intent = new Intent(MainActivity.this,Profile.class);
            String token = getAuthToken();
            intent.putExtra("token",token);
            startActivity(intent);
            finish(); // Close MainActivity
        }

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
            // To go from Main Activity to Important Temples
            TextView temples = findViewById(R.id.ImpTemples);
            temples.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(MainActivity.this, ImportantTemples.class);
                    intent.putExtra("checkPrev","main" );
                    startActivity(intent);
                    startActivity(intent);
                }
            });

            // To go from Main Activity to Mission
            TextView mission = findViewById(R.id.ourMission);
            mission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(MainActivity.this, Mission.class);
                    intent.putExtra("checkPrev","main" );
                    startActivity(intent);
                }
            });

            // To go from Main Activity to Glory of Ram Naam
            TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
            ramNaamGlory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(MainActivity.this, GloryOfRamnaam.class);
                    intent.putExtra("checkPrev","main" );
                    startActivity(intent);
                }
            });

            // To go from Main Activity to Writing Experience
            TextView write = findViewById(R.id.writingExperience);
            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(MainActivity.this, WritingExperience.class);
                    intent.putExtra("checkPrev","main" );
                    startActivity(intent);
                }
            });

            // To go from Main Activity to Mobile Number Login
            TextView mobLogin = findViewById(R.id.loginMobile);
            mobLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(MainActivity.this, MobileNumberLogin.class);
                    startActivity(intent);
                }
            });


            // To go from Main Activity to Register
            Button register_button = findViewById(R.id.register_button);
            register_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, Register.class);
                    startActivity(intent);
                }
            });

        }
        // End of Switching from one activity to another code

        // Scroll down to particular section
        {
            Button scrollToButton = findViewById(R.id.goToLoginButton);
            final ScrollView scrollView = findViewById(R.id.scrollToButton);

            scrollToButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int scrollToY = findViewById(R.id.card_view).getTop(); // Adjust with the ID of the target section
                    scrollView.smoothScrollTo(0, scrollToY);
                }
            });
        }
        // End of Scroll down to particular section

        // ---------------------------------------------------------------------------------------------------------------

        // -----------------------------------------------API Functionality----------------------------------------------------


        // -------------------Setting Total Ramcount and Total Registred Devotees values------------------------
        {
            TextView TextViewTotalRamCount = findViewById(R.id.TextViewTotalRamCount);
            TextView TextViewRegisteredDevotees = findViewById(R.id.TextViewRegisteredDevotees);

            String api = "https://restapiramji.onrender.com/user";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String ramCount = response.getString("totalRamnaamCount");
                        TextViewTotalRamCount.setText(ramCount);

                        String registredDevotees = response.getString(("userCount"));
                        TextViewRegisteredDevotees.setText(registredDevotees);

                    } catch (Exception e) {
                        Log.d("catch", "users data");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            Volley.newRequestQueue(this).add(request);
        }

        //----------------------------End of Setting Total Ramcount and Total Registred Devotees values-------------------


        // ------------------------Login Click -> Main Activity to Profile Activity------------------------------------------
        {
            // Find references to EditText fields and Button
            EditText editTextLoginID = findViewById(R.id.editTextLoginID);
            EditText editTextPassword = findViewById(R.id.editTextPassword);
            Button loginButton = findViewById(R.id.login_button);

            // Set OnClickListener to the loginButton
            loginButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                @Override
                public void onClick(View v) {
                    // Get text from EditText fields
                    Log.d("login", "clicked on login");
                    String loginID = editTextLoginID.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();

                    // Make sure fields are not empty
                    if (!loginID.isEmpty() && !password.isEmpty()) {
                        // Create a request body with URL-encoded form data
                        String requestBody = "username=" + URLEncoder.encode(loginID, StandardCharsets.UTF_8)
                                + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

                        // Make POST request using Volley
                        String api = "https://restapiramji.onrender.com/auth/login"; // login API endpoint
                        StringRequest request = new StringRequest(Request.Method.POST, api,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Handle response
                                        try {
                                            JSONObject jsonResponse = new JSONObject(response);
                                            String token = jsonResponse.getString("token");


                                            // Check for admin credentials
                                            if (loginID.equals("INext") && password.equals("INextETS")) {
                                                // Admin credentials entered, navigate to AdminDashboard
                                                Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                                                intent.putExtra("token", token);
                                                startActivity(intent);
                                            } else {
                                                // Save login status
                                                saveLoginStatus(true);
                                                // Save token
                                                saveAuthToken(token);

                                                // Start the Profile activity
                                                Intent intent = new Intent(MainActivity.this, Profile.class);
                                                intent.putExtra("token", token);
                                                startActivity(intent);
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            // Unexpected response format, show an error message
                                            Toast.makeText(MainActivity.this, "Unexpected response. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // Handle error
                                        if (error.networkResponse != null && error.networkResponse.data != null) {
                                            String errorMessage = new String(error.networkResponse.data);
                                            if (errorMessage.contains("password not match")) {
                                                // Password doesn't match, display toast message
                                                Toast.makeText(MainActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                                            } else if (errorMessage.contains("user not found")) {
                                                // User not found, display toast message
                                                Toast.makeText(MainActivity.this, "User not found. Please check your credentials.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Other errors, show a generic error message
                                                Toast.makeText(MainActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            // No response from server, show a generic error message
                                            Toast.makeText(MainActivity.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }) {
                            @Override
                            public byte[] getBody() {
                                return requestBody.getBytes(StandardCharsets.UTF_8);
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }
                        };

                        // Add request to Volley request queue
                        Volley.newRequestQueue(MainActivity.this).add(request);
                    } else {
                        // If any field is empty, show a message to the user
                        Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        //------------------------End of Login Click -> Main Activity to Profile Activity-----------------------------------------
    }

    // ---------------------------------------------------------------------------------------------------------------
    // Code for Session Management

    // Method to save login status
    public void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }

    // Method to check if the user is already logged in
    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private void saveAuthToken(String authToken) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("authToken", authToken);
        editor.apply();
    }

    private String getAuthToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("authToken", null);
    }

    // End of  Code for not logging again and again

    // ---------------------------------------------------------------------------------------------------------------------
}