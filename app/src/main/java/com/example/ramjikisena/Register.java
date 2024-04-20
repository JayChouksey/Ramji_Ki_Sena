package com.example.ramjikisena;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
            // To go from Register to Important Temples
            TextView temples = findViewById(R.id.ImpTemples);
            temples.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(Register.this, ImportantTemples.class);
                    intent.putExtra("checkPrev","register");
                    startActivity(intent);
                }
            });

            // To go from Register to Mission
            TextView mission = findViewById(R.id.ourMission);
            mission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(Register.this, Mission.class);
                    intent.putExtra("checkPrev","register");
                    startActivity(intent);
                }
            });

            // To go from Register to Glory of Ram Naam
            TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
            ramNaamGlory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(Register.this, GloryOfRamnaam.class);
                    intent.putExtra("checkPrev","register");
                    startActivity(intent);
                }
            });

            // To go from Register to Writing Experience
            TextView write = findViewById(R.id.writingExperience);
            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(Register.this, WritingExperience.class);
                    intent.putExtra("checkPrev","register");
                    startActivity(intent);
                }
            });

            // To go from Register to Login
            Button goToLogin = findViewById(R.id.go_to_login);
            goToLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }
        // End of Switching from one activity to another code

        // Scroll down to particular section
        {
            Button scrollToButton = findViewById(R.id.go_to_register);
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

        // ------------------------------------------------------------------------------------------------------------

        // --------------------Sending the data of user to database-----------------------------------------------------
        {
        // Find references to EditText fields and Button
        EditText editTextUserLoginID = findViewById(R.id.editTextUserLoginID);
        EditText editTextPassword = findViewById(R.id.editTextPassword);
        EditText editTextMobileNo = findViewById(R.id.editTextMobileNo);
        EditText editTextFullName = findViewById(R.id.editTextFullName);
        EditText editTextCity = findViewById(R.id.editTextCity);
        Button registerButton = findViewById(R.id.register_button);

        // Set OnClickListener to the registerButton
            registerButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                @Override
                public void onClick(View v) {
                    registerButton.setText("LOADING...");
                    // Get text from EditText fields
                    String userLoginID = editTextUserLoginID.getText().toString().trim();
                    String password = editTextPassword.getText().toString().trim();
                    String mobileNo = editTextMobileNo.getText().toString().trim();
                    String fullName = editTextFullName.getText().toString().trim();
                    String city = editTextCity.getText().toString().trim();

                    // Make sure fields are not empty
                    if (!userLoginID.isEmpty() && !password.isEmpty() && !mobileNo.isEmpty() && !fullName.isEmpty() && !city.isEmpty()) {
                        // Validate if the mobile number has exactly 10 digits
                        if (mobileNo.length() == 10) {
                            // Create a request body with URL-encoded form data
                            String requestBody = "username=" + URLEncoder.encode(userLoginID, StandardCharsets.UTF_8)
                                    + "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8)
                                    + "&contact=" + URLEncoder.encode(mobileNo, StandardCharsets.UTF_8)
                                    + "&name=" + URLEncoder.encode(fullName, StandardCharsets.UTF_8)
                                    + "&city=" + URLEncoder.encode(city, StandardCharsets.UTF_8);

                            // Make POST request using Volley
                            String api = "https://api.ramjikisena.com/auth/register";
                            StringRequest request = new StringRequest(Request.Method.POST, api,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            // Handle response if needed
                                            // For example, show a success message
                                            Toast.makeText(Register.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                                            registerButton.setText("REGISTER");
                                            Intent intent = new Intent(Register.this, MainActivity.class);
                                            startActivity(intent);
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error if needed
                                    // For example, show an error message
                                    Toast.makeText(Register.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    registerButton.setText("REGISTER");
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
                            Volley.newRequestQueue(Register.this).add(request);
                        } else {
                            // If mobile number does not have 10 digits, show a message to the user
                            Toast.makeText(Register.this, "Please enter a 10-digit mobile number", Toast.LENGTH_SHORT).show();
                            registerButton.setText("REGISTER");
                        }
                    } else {
                        // If any field is empty, show a message to the user
                        Toast.makeText(Register.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                        registerButton.setText("REGISTER");
                    }
                }
            });

        }
        // --------------------End of Sending the data of user to database-----------------------------------------------------

    }
}