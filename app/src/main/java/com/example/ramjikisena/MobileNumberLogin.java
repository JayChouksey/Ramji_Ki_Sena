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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton$InspectionCompanion;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MobileNumberLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_login);

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

            // To go from Login Button to Main Activity
            Button loginButtonTop = findViewById(R.id.LoginButtonTop);
            final ScrollView scrollView = findViewById(R.id.scroll);

            loginButtonTop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int scrollToY = findViewById(R.id.card_view).getTop(); // Adjust with the ID of the target section
                    scrollView.smoothScrollTo(0, scrollToY);
                }
            });

            // To go from Login Button to Profile
            Button buttonRegister = findViewById(R.id.buttonRegister);
            buttonRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MobileNumberLogin.this, Register.class);
                    startActivity(intent);
                }
            });

            // To go from Login With Password to Main Activity
            TextView loginPassword = findViewById(R.id.loginPassword);
            loginPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(MobileNumberLogin.this, MainActivity.class);
                    startActivity(intent);
                }
            });

        }
        // End of Switching from one activity to another code

        // ---------------------------------------------------------------------------------------------------------------


        // ------------------------Login Click -> Mobile Number Login to Profile Activity/AdminDashboard------------------------------------------
        {
            // Find references to EditText fields and Button
            EditText editTextMobileNum = findViewById(R.id.editTextMobNum);
            Button loginButton = findViewById(R.id.buttonLogin);

            // Set OnClickListener to the loginButton
            loginButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                @Override
                public void onClick(View v) {
                    String phoneNumber = editTextMobileNum.getText().toString().trim();

                    if (!phoneNumber.isEmpty()) {
                        // Validate if the phone number has exactly 10 digits
                        if (phoneNumber.length() == 10) {
                            // Create a request body with URL-encoded form data
                            String requestBody = "contact=" + URLEncoder.encode(phoneNumber, StandardCharsets.UTF_8);

                            // Make POST request using Volley
                            String api = "https://api.ramjikisena.com/auth/forgot"; // login API endpoint
                            StringRequest request = new StringRequest(Request.Method.POST, api,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                // Handle response if needed
                                                JSONObject jsonResponse = new JSONObject(response);
                                                String token = jsonResponse.getString("token");

                                                // Save login status
                                                saveLoginStatus(true);
                                                // Save token
                                                saveAuthToken(token);

                                                // Start the Profile activity or navigate to the next screen
                                                Intent intent = new Intent(MobileNumberLogin.this, Profile.class);
                                                intent.putExtra("token", token);
                                                startActivity(intent);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error if needed
                                    Toast.makeText(MobileNumberLogin.this, "Login failed. Please try again.", Toast.LENGTH_SHORT).show();
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
                            Volley.newRequestQueue(MobileNumberLogin.this).add(request);
                        } else {
                            // If phone number does not have 10 digits, show a message to the user
                            Toast.makeText(MobileNumberLogin.this, "Please enter a 10-digit phone number", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // If phone number is empty, show a message to the user
                        Toast.makeText(MobileNumberLogin.this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        //------------------------End of Login Click ->Mobile Number Login to Profile Activity-----------------------------------------
    }

    // -------------------------------------------------------------------------------------------------------------------
    // Session Management

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
