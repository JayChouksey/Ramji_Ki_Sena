 package com.example.ramjikisena;



import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
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
import com.example.ramjikisena.drawerfiles.About;
import com.example.ramjikisena.drawerfiles.ContactUs;
import com.example.ramjikisena.drawerfiles.Feedback;
import com.example.ramjikisena.drawerfiles.Gallery;
import com.example.ramjikisena.drawerfiles.HelpAndSupport;
import com.example.ramjikisena.drawerfiles.PostAd;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Profile extends AppCompatActivity{

    // For language and music drop down
    private PopupWindow popupWindow;
    Button languageButton;
    Button musicButton;
    MediaPlayer player;
    ImageView button_play_pause, button_mute_sound;
    private SeekBar seekBar;
    private TextView textProgress, textTotal;
    private boolean isPlaying = true; // Assuming that initially, the player is playing
    private boolean isMute = false; // Assuming that initially, the player is in sound mode
    private int pausedPosition = 0; // Variable to store the paused position of the song

    // for User data
    private TextView textViewRank;
    private TextView textViewCurCount;
    private TextView textViewTotalCount;
    private TextView textViewMalaCount;
    private TextView textViewUserName;

    // for drawer navigation
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RelativeLayout customDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private boolean isDrawerOpen = false;
    private final int DRAWER_WIDTH = 250; // Width of the drawer layout in dp

    // API Links
    String urlLogOut = "https://restapiramji.onrender.com/auth/logout"; // API endpoint for logout
    String urlSave = "https://restapiramji.onrender.com/user/save"; // API endpoint for save

    // Save checks
    private boolean isSave = true;
    private boolean allowSave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        // Language and Music drop down code
        {

            // Initialize buttons
            languageButton = findViewById(R.id.languageButton);
            musicButton = findViewById(R.id.musicButton);

            // Set click listener for language button
            languageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupLang(v);
                }
            });

            // Set click listener for language button
            musicButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showPopupMusic(v);
                }
            });

            // player components initialization
            button_play_pause = findViewById(R.id.button_play_pause);
            button_mute_sound = findViewById(R.id.button_mute_sound);
            seekBar = findViewById(R.id.seekbar);
            textProgress = findViewById(R.id.text_progress);
            textTotal = findViewById(R.id.text_total);


        }
        // End of Language drop down code

        // -------------------------------------------------------------------------------------------------------------------

        // Text Movement Code
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
                    if(isSave){
                        // Start the second activity
                        Intent intent = new Intent(Profile.this, ImportantTemples.class);
                        intent.putExtra("checkPrev","profile" );
                        startActivity(intent);
                    }else{
                        showDialog(); //
                    }

                }
            });

            // To go from Main Activity to Mission
            TextView mission = findViewById(R.id.ourMission);
            mission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSave){
                        // Start the second activity
                        Intent intent = new Intent(Profile.this, Mission.class);
                        intent.putExtra("checkPrev","profile" );
                        startActivity(intent);
                    }
                    else{
                        showDialog();
                    }
                }
            });

            // To go from Main Activity to Glory of Ram Naam
            TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
            ramNaamGlory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSave){
                        // Start the second activity
                        Intent intent = new Intent(Profile.this, GloryOfRamnaam.class);
                        intent.putExtra("checkPrev","profile" );
                        startActivity(intent);
                    }
                    else{
                        showDialog();
                    }

                }
            });

            // To go from Main Activity to Writing Experience
            TextView write = findViewById(R.id.writingExperience);
            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSave){
                        // Start the second activity
                        Intent intent = new Intent(Profile.this, WritingExperience.class);
                        intent.putExtra("checkPrev","profile" );
                        startActivity(intent);
                    }
                    else{
                        showDialog();
                    }

                }
            });

            // Get the authorization token passed from the previous activity
            //Intent intentHistory = getIntent();
            String tokenHistory = getAuthToken() ;

            Button lekhanHistoryButton = findViewById(R.id.button_LekhanHistory);
            lekhanHistoryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSave){
                        Intent intent = new Intent(Profile.this, LekhanHistory.class);
                        intent.putExtra("token",tokenHistory);
                        startActivity(intent);
                    }
                    else{
                        showDialog();
                    }

                }
            });

            // Get the authorization token passed from the previous activity
            // Intent intentDevotee = getIntent();
            String tokenDevotee = getAuthToken();

            Button allDevoteesButton = findViewById(R.id.button_AllDevotees);
            allDevoteesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSave){
                        Intent intent = new Intent(Profile.this, AllDevotees.class);
                        intent.putExtra("token", tokenDevotee);
                        startActivity(intent);
                    }
                    else{
                        showDialog();
                    }
                }
            });
        }
        // End of Switching from one activity to another code

        // Scroll down to particular section
        {
            Button scrollToButton = findViewById(R.id.goToHomeButton);
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

        EditText editTextRamNaam = findViewById(R.id.editTextRamNaam);
        editTextRamNaam.setKeyListener(null); // disable the keyboard input
        // --------------------------------------------RAM Button actions-------------------------------------------------------
        {
        // Initialize Buttons
        Button buttonR = findViewById(R.id.button_R);
        Button buttonA = findViewById(R.id.button_A);
        Button buttonM = findViewById(R.id.button_M);

        // disabled the button
        buttonA.setEnabled(false);
        buttonM.setEnabled(false);


        // Set OnClickListener for button R
        buttonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSave = false; // done to check, user can't move without saving the data

                // To give animation to button
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(100); // Set animation duration
                scaleAnimation.setRepeatCount(1); // Set repeat count (1 means it will animate once)
                scaleAnimation.setRepeatMode(Animation.REVERSE); // Set repeat mode to reverse

                // Apply animation to the button
                v.startAnimation(scaleAnimation);

                // Append "र" to the EditText
                editTextRamNaam.append("र");

                buttonA.setEnabled(true);
                buttonR.setEnabled(false);
                buttonM.setEnabled(false);
            }
        });

        // Set OnClickListener for button A
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // To give animation to button
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(100); // Set animation duration
                scaleAnimation.setRepeatCount(1); // Set repeat count (1 means it will animate once)
                scaleAnimation.setRepeatMode(Animation.REVERSE); // Set repeat mode to reverse

                // Apply animation to the button
                v.startAnimation(scaleAnimation);

                // Append "ा" to the EditText
                editTextRamNaam.append("ा");

                buttonM.setEnabled(true);
                buttonA.setEnabled(false);
                buttonR.setEnabled(false);
            }
        });

        // Set OnClickListener for button M
        buttonM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // To give animation to button
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(100); // Set animation duration
                scaleAnimation.setRepeatCount(1); // Set repeat count (1 means it will animate once)
                scaleAnimation.setRepeatMode(Animation.REVERSE); // Set repeat mode to reverse

                // Apply animation to the button
                v.startAnimation(scaleAnimation);

                // Append "म" to the EditText
                editTextRamNaam.append("म ");

                // Update TextViewCurCount and TextViewTotalCount
                String curCountString = textViewCurCount.getText().toString();
                String totalCountString = textViewTotalCount.getText().toString();

                int curCount = Integer.parseInt(curCountString) + 1;
                int totalCount = Integer.parseInt(totalCountString) + 1;

                textViewCurCount.setText(String.valueOf(curCount));
                textViewTotalCount.setText(String.valueOf(totalCount));

                // Calculate TextViewMalaCount
                double malaCount = (double) totalCount / 108;
                String formattedMalaCount = String.format("%.2f", malaCount);
                textViewMalaCount.setText(formattedMalaCount);

                buttonR.setEnabled(true);
                buttonM.setEnabled(false);
                buttonA.setEnabled(false);
                allowSave = true; // allow to save
            }
        });

        // -------------------------------------------Language Button ------------------------------------------------------


        }
        // -----------------------------------------End of RAM Button actions-------------------------------------------------------

        // ------------------------------------------------------------------------------------------------------

        // ---------------------------------- Drawer Navigation Code -------------------------------------------------
        {
            // Find the menuButton
            Button menuButton = findViewById(R.id.menuButton);
            customDrawerLayout = findViewById(R.id.custom_drawer_layout);

            // Set click listener on menu button to toggle drawer
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isDrawerOpen) {
                        openDrawer();
                    } else {
                        closeDrawer();
                    }
                }
            });
            // Set touch listener on the main layout to close the drawer when touched
            findViewById(android.R.id.content).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (isDrawerOpen && event.getAction() == MotionEvent.ACTION_DOWN) {
                        closeDrawer();
                    }
                    return false;
                }
            });

            TextView drawerPostAd, drawerAbout, drawerGallery, drawerShare, drawerFeedback, drawerHelp, drawerCall;
            drawerPostAd = findViewById(R.id.drawer_postAd);
            drawerAbout = findViewById(R.id.drawer_about);
            drawerGallery = findViewById(R.id.drawer_gallery);
            drawerShare = findViewById(R.id.drawer_share);
            drawerFeedback = findViewById(R.id.drawer_feedback);
            drawerHelp = findViewById(R.id.drawer_help);
            drawerCall = findViewById(R.id.drawer_call);

            drawerPostAd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, PostAd.class);
                    startActivity(intent);
                    closeDrawer();
                }
            });
            drawerAbout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, About.class);
                    startActivity(intent);
                    closeDrawer();
                }
            });
            drawerGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, Gallery.class);
                    startActivity(intent);
                    closeDrawer();
                }
            });
            drawerFeedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, Feedback.class);
                    startActivity(intent);
                    closeDrawer();
                }
            });
            drawerHelp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, HelpAndSupport.class);
                    startActivity(intent);
                    closeDrawer();
                }
            });

            drawerCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Profile.this, ContactUs.class);
                    startActivity(intent);
                    closeDrawer();
                }
            });
            drawerShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(Profile.this,"Share Clicked",Toast.LENGTH_SHORT).show();
                    // Your app's package name on the Play Store
                    String appPackageName = getPackageName();
                    // Play Store URL of your app
                    String appUrl = "https://www.ramjikisena.com/" + appPackageName;

                    // Create an Intent with ACTION_SEND to share the app link
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this app!");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "Download and install the app: " + appUrl);

                    // Start the activity for sharing
                    startActivity(Intent.createChooser(shareIntent, "Share via"));

                    closeDrawer();
                }
            });




            /*drawerLayout = findViewById(R.id.drawer_layout);
            navigationView = findViewById(R.id.nav_view);
                // Set click listener on menu button to open/close the drawer
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (drawerLayout.isDrawerOpen(navigationView)) {
                        drawerLayout.closeDrawer(navigationView);
                    } else {
                        drawerLayout.openDrawer(navigationView);
                    }
                }
            });*/


        }
        // ---------------------------------- End of Drawer Navigation Code -------------------------------------------------


        // ----------------------------------------API calls----------------------------------------------------------------------

        // Get the authorization token passed from the previous activity
        String token = getAuthToken();


        // Collecting profile data using API
        {
        // Initialize TextViews
        textViewRank = findViewById(R.id.TextViewRank);
        textViewCurCount = findViewById(R.id.TextViewCurCount);
        textViewTotalCount = findViewById(R.id.TextViewTotalCount);
        textViewMalaCount = findViewById(R.id.TextViewMalaCount);
        textViewUserName = findViewById(R.id.TextViewUserName);


        // Make a GET request to the profile endpoint
        String apiProfile = "https://restapiramji.onrender.com/user/profile"; // API endpoint

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, apiProfile, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject jsonObject = response.getJSONObject("user");

                    String currCount = jsonObject.getString("currCount");
                    String totalCount = jsonObject.getString("totalCount");
                    String rank = jsonObject.getString("rank");
                    String mala = jsonObject.getString("mala");
                    String userName = jsonObject.getString("username");

                    // after extracting all the data we are setting that data to all our views.
                    textViewRank.setText(rank);
                    textViewCurCount.setText(currCount);
                    textViewTotalCount.setText(totalCount);
                    textViewMalaCount.setText(mala);
                    textViewUserName.setText(userName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Profile.this, "Error in getting data", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap header = new HashMap<>();

                header.put("authorization", token);

                return header;
            }
        };

        queue.add(jsonObjectRequest);
    }
        // End of Collecting profile data using API

        // ----------------------------------------------------------------------------------------------------

        // -------------------------------Log Out Button implementation using API---------------------------------------------
        {
            Button buttonLogOut = findViewById(R.id.button_LogOut);

            buttonLogOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isSave){
                        Toast.makeText(Profile.this, "Logout done", Toast.LENGTH_SHORT).show();
                        logout(); // user defined function
                    }
                    else{
                        showDialog();
                    }

                }

            });
        }
        // --------------------------End of Log Out Button implementation using API---------------------------------------------

        // ------------------------------------------------------------------------------------------------------------------------

        // ----------------------------------------Save button---------------------------------------------------------------
            Button buttonSave = findViewById(R.id.button_save);
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(allowSave){
                        isSave = true; // if saved, user is allowed to change the screen

                        // To give animation to button
                        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 0.9f,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation.setDuration(100); // Set animation duration
                        scaleAnimation.setRepeatCount(1); // Set repeat count (1 means it will animate once)
                        scaleAnimation.setRepeatMode(Animation.REVERSE); // Set repeat mode to reverse

                        // Apply animation to the button
                        v.startAnimation(scaleAnimation);

                        // Get the text from the TextView - CurCount, TotalCount, MalaCount
                        String curCountString = textViewCurCount.getText().toString();
                        int curCount = Integer.parseInt(curCountString);
                        String totalCountString = textViewTotalCount.getText().toString();
                        String malaCountString = textViewMalaCount.getText().toString();

                        // Create a JSON object for the request body
                        JSONObject requestBody = new JSONObject();
                        try {
                            requestBody.put("currentCount", curCount);
                            requestBody.put("totalCount", totalCountString);
                            requestBody.put("malaCount", malaCountString);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }

                        // Make a POST request using Volley
                        StringRequest request = new StringRequest(Request.Method.POST, urlSave,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Handle successful response
                                        Toast.makeText(Profile.this, "आपका राम धन सेव हो गया", Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Handle error response
                                Toast.makeText(Profile.this, "Error saving data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                            @Override
                            public byte[] getBody() {
                                return requestBody.toString().getBytes();
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/json";
                            }

                            @Override
                            public Map<String, String> getHeaders() {
                                Map<String, String> headers = new HashMap<>();
                                headers.put("authorization", token);
                                return headers;
                            }
                        };

                        // Add the request to the RequestQueue
                        Volley.newRequestQueue(Profile.this).add(request);


                        editTextRamNaam.setText("");
                        textViewCurCount.setText("0");
                    }

                }
            });
        // ----------------------------------------End of Save button---------------------------------------------------------------

    }

    // Dialog box function
    private void showDialog(){
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.layout_dialog_box);

        Button OK = dialog.findViewById(R.id.btn_ok);

        OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    //--------------------------------------- Log out function for API call --------------------------------------------------
    private void logout() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Request a JSON response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlLogOut, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Check if success is true
                            boolean success = response.getBoolean("success");
                            if (success) {
                                // Clear authentication token
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                clearAuthToken();
                                editor.clear();
                                editor.commit();

                                // Logout successful, navigate to MainActivity
                                Intent intent = new Intent(Profile.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Close the current activity
                            } else {
                                // Logout failed, show error message
                                Toast.makeText(Profile.this, "Logout failed. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle JSON parsing error
                            Toast.makeText(Profile.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(Profile.this, "Logout failed. Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            // Override getHeaders() to add the authorization token
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Get the token from SharedPreferences
                String authToken = getAuthToken();
                // Create a map to store headers
                Map<String, String> headers = new HashMap<>();
                // Add the authorization token to the headers
                headers.put("authorization", authToken);
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }


    // Shared Preference functtions
    // Method to clear authentication token
    private void clearAuthToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("authToken");
        editor.apply();
    }

    // Method to retrieve authentication token
    private String getAuthToken() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("authToken", null);
    }

    //--------------------------------------- Log out function for API call --------------------------------------------------

    private void showPopupLang(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_language, null);

        // Create a popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // Make it focusable
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Set content view and background color
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Find radio buttons in popup layout
        RadioButton englishRadioButton = popupView.findViewById(R.id.englishRadioButton);
        RadioButton hindiRadioButton = popupView.findViewById(R.id.hindiRadioButton);
        TextView englishText = popupView.findViewById(R.id.englishText);
        TextView hindiText = popupView.findViewById(R.id.hindiText);

        // Find the textview and buttons to change its language
        TextView movingText = findViewById(R.id.movingText);
        TextView AppName = findViewById(R.id.app_name);
        TextView GotoTitle = findViewById(R.id.GoToTitle);
        TextView ImpTemples = findViewById(R.id.ImpTemples);
        TextView ourMission = findViewById(R.id.ourMission);
        TextView gloryOfRamNaam = findViewById(R.id.gloryOfRamNaam);
        TextView writingExperience = findViewById(R.id.writingExperience);
        TextView Welcome = findViewById(R.id.Welcome);
        TextView JayShriRam = findViewById(R.id.JayShriRam);
        TextView Rank = findViewById(R.id.Rank);
        TextView CurrentCount = findViewById(R.id.CurrentCount);
        TextView TotalCount = findViewById(R.id.TotalCount);
        TextView MalaCount = findViewById(R.id.MalaCount);
        TextView movingTextBottom = findViewById(R.id.movingTextBottom);

        Button goToHomeButton = findViewById(R.id.goToHomeButton);
        Button button_LekhanHistory = findViewById(R.id.button_LekhanHistory);

        // Set click listener for radio buttons
        englishRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle English radio button click
                languageButton.setText("English");

                // Change text to English
                englishText.setText("English");
                hindiText.setText("Hindi");

                movingText.setText("Jayshri Gayatri Food Products Pvt. Ltd. Milk magic");
                AppName.setText("Ramji Ki Sena");
                GotoTitle.setText("Daily Darshan & Ram naam Meditation \uD83D\uDC49");
                ImpTemples.setText("\uD83D\uDEA9 Important temples of Ayodhya");
                ourMission.setText("\uD83C\uDFAF Our Missions");
                gloryOfRamNaam.setText("\uD83D\uDCD6 Glory of Ram naam");
                writingExperience.setText("✍\uFE0F Writing experience & feedback \n");
                Welcome.setText("Welcome to Ramji Ki Sena");
                JayShriRam.setText("Jay Shri Ram - \uD83D\uDE4E");
                Rank.setText("Your Rank");
                CurrentCount.setText("Current Ramnaam Count");
                TotalCount.setText("Total Ramnaam Count");
                MalaCount.setText("Total Mala Count");
                movingTextBottom.setText("Jayshri Gayatri Food Products Pvt. Ltd. Milk magic");

                goToHomeButton.setText("Home");
                button_LekhanHistory.setText("Lekhan \nHistory");


                popupWindow.dismiss(); // Dismiss the popup
            }
        });

        hindiRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Hindi radio button click
                languageButton.setText("हिंदी");

                // Change text to Hindi
                englishText.setText("अंग्रेजी");
                hindiText.setText("हिंदी");

                movingText.setText("जय श्री गायत्री फूड प्रोडक्ट्स प्रा. लिमिटेड मिल्क मैजिक");
                AppName.setText("रामजी की सेना");
                GotoTitle.setText("दैनिक दर्शन एवं राम नाम ध्यान \uD83D\uDC49");
                ImpTemples.setText("\uD83D\uDEA9 अयोध्या के प्रमुख मंदिर");
                ourMission.setText("\uD83C\uDFAF हमारा मिशन");
                gloryOfRamNaam.setText("\uD83D\uDCD6 राम नाम की महिमा");
                writingExperience.setText("✍\uFE0F लेखन अनुभव और प्रतिक्रिया \n");
                Welcome.setText("रामजी की सेना में आपका स्वागत है");
                JayShriRam.setText("जय श्री राम - \uD83D\uDE4E");
                Rank.setText("आपका रैंक");
                CurrentCount.setText("वर्तमान रामनाम गिनती");
                TotalCount.setText("कुल रामनाम गिनती");
                MalaCount.setText("कुल माला गिनती");
                movingTextBottom.setText("जय श्री गायत्री फूड प्रोडक्ट्स प्रा. लिमिटेड मिल्क मैजिक");

                goToHomeButton.setText("घर");
                button_LekhanHistory.setText("लेखन \n इतिहास");

                popupWindow.dismiss(); // Dismiss the popup

            }
        });

        // Show the popup at the bottom of the anchor view
        popupWindow.showAsDropDown(anchorView);
    }

    private void showPopupMusic(View anchorView) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.layout_music, null);

        // Create a popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // Make it focusable
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Set content view and background color
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        // Find radio buttons in popup layout
        RadioButton music0RadioButton = popupView.findViewById(R.id.Music0RadioButton);
        RadioButton music1RadioButton = popupView.findViewById(R.id.Music1RadioButton);
        RadioButton music2RadioButton = popupView.findViewById(R.id.Music2RadioButton);
        RadioButton music3RadioButton = popupView.findViewById(R.id.Music3RadioButton);
        RadioButton music4RadioButton = popupView.findViewById(R.id.Music4RadioButton);
        RadioButton music5RadioButton = popupView.findViewById(R.id.Music5RadioButton);
        RadioButton music6RadioButton = popupView.findViewById(R.id.Music6RadioButton);

        // Set click listener for radio buttons
        music0RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music0 radio button click
                // Change text or perform action for English
                musicButton.setText("कोई भजन नहीं");
                stopPlayer();
                seekBar.setProgress(0); // Reset seek bar to initial position
                popupWindow.dismiss(); // Dismiss the popup
            }
        });

        music1RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music1 radio button click
                // Change text or perform action for Music1
                musicButton.setText("राम धुन 1");
                stopPlayer();
                play1(v);
                popupWindow.dismiss(); // Dismiss the popup
            }
        });

        music2RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music2 radio button click
                // Change text or perform action for Music2
                musicButton.setText("राम धुन 2");
                stopPlayer();
                play2(v);
                popupWindow.dismiss(); // Dismiss the popup
            }
        });


        music3RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music3 radio button click
                // Change text or perform action for Music3
                musicButton.setText("राम धुन 3");
                stopPlayer();
                play3(v);
                popupWindow.dismiss(); // Dismiss the popup
            }
        });

        music4RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music4 radio button click
                // Change text or perform action for Music4
                musicButton.setText("राम धुन 4");
                stopPlayer();
                play4(v);
                popupWindow.dismiss(); // Dismiss the popup

            }
        });

        music5RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music5 radio button click
                // Change text or perform action for Music5
                musicButton.setText("राम धुन 5");
                stopPlayer();
                play5(v);
                popupWindow.dismiss(); // Dismiss the popup

            }
        });

        music6RadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle Music6 radio button click
                // Change text or perform action for Music6
                musicButton.setText("राम धुन 6");
                stopPlayer();
                play6(v);
                popupWindow.dismiss(); // Dismiss the popup

            }
        });

        // Show the popup at the bottom of the anchor view
        popupWindow.showAsDropDown(anchorView);
    }

    // Music functions
    public void play1(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.bgaudio);
            textTotal.setText(formatTime(player.getDuration()));

            // seek bar progress code
            seekBar.setMax(player.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress text view
                    textProgress.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Pause the player when the user starts seeking
                    pause(v);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Seek to the selected position if player is not null
                    if (player != null) {
                        player.seekTo(seekBar.getProgress());
                        // Resume playing if it was playing before seeking
                        if (isPlaying) {
                            player.start();
                        }
                    }
                }
            });


            // Update SeekBar and textProgress automatically with song progress
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (player != null && player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        textProgress.setText(formatTime(currentPosition));
                    }
                    handler.postDelayed(this, 1000); // Update every second
                }
            }, 1000); // Delay 1 second for the first update
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    // Restart the player from the beginning
                    mp.seekTo(0);
                    mp.start();

                    // stopPlayer();
                }
            });
        }

        player.start();
    }

    public void play2(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.sitaram);
            textTotal.setText(formatTime(player.getDuration()));

            // seek bar progress code
            seekBar.setMax(player.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress text view
                    textProgress.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Pause the player when the user starts seeking
                    pause(v);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Seek to the selected position if player is not null
                    if (player != null) {
                        player.seekTo(seekBar.getProgress());
                        // Resume playing if it was playing before seeking
                        if (isPlaying) {
                            player.start();
                        }
                    }
                }
            });

            // Update SeekBar and textProgress automatically with song progress
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (player != null && player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        textProgress.setText(formatTime(currentPosition));
                    }
                    handler.postDelayed(this, 1000); // Update every second
                }
            }, 1000); // Delay 1 second for the first update
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    // Restart the player from the beginning
                    mp.seekTo(0);
                    mp.start();

                    // stopPlayer();
                }
            });
        }

        player.start();
    }

    public void play3(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.heyram);
            textTotal.setText(formatTime(player.getDuration()));

            // seek bar progress code
            seekBar.setMax(player.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress text view
                    textProgress.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Pause the player when the user starts seeking
                    pause(v);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Seek to the selected position if player is not null
                    if (player != null) {
                        player.seekTo(seekBar.getProgress());
                        // Resume playing if it was playing before seeking
                        if (isPlaying) {
                            player.start();
                        }
                    }
                }
            });


            // Update SeekBar and textProgress automatically with song progress
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (player != null && player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        textProgress.setText(formatTime(currentPosition));
                    }
                    handler.postDelayed(this, 1000); // Update every second
                }
            }, 1000); // Delay 1 second for the first update
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    // Restart the player from the beginning
                    mp.seekTo(0);
                    mp.start();

                    // stopPlayer();
                }
            });
        }

        player.start();
    }

    public void play4(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.haridhun);
            textTotal.setText(formatTime(player.getDuration()));

            // seek bar progress code
            seekBar.setMax(player.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress text view
                    textProgress.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Pause the player when the user starts seeking
                    pause(v);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Seek to the selected position if player is not null
                    if (player != null) {
                        player.seekTo(seekBar.getProgress());
                        // Resume playing if it was playing before seeking
                        if (isPlaying) {
                            player.start();
                        }
                    }
                }
            });


            // Update SeekBar and textProgress automatically with song progress
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (player != null && player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        textProgress.setText(formatTime(currentPosition));
                    }
                    handler.postDelayed(this, 1000); // Update every second
                }
            }, 1000); // Delay 1 second for the first update
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    // Restart the player from the beginning
                    mp.seekTo(0);
                    mp.start();

                    // stopPlayer();
                }
            });
        }

        player.start();
    }

    public void play5(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.ramnamkirtan);
            textTotal.setText(formatTime(player.getDuration()));

            // seek bar progress code
            seekBar.setMax(player.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress text view
                    textProgress.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Pause the player when the user starts seeking
                    pause(v);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Seek to the selected position if player is not null
                    if (player != null) {
                        player.seekTo(seekBar.getProgress());
                        // Resume playing if it was playing before seeking
                        if (isPlaying) {
                            player.start();
                        }
                    }
                }
            });


            // Update SeekBar and textProgress automatically with song progress
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (player != null && player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        textProgress.setText(formatTime(currentPosition));
                    }
                    handler.postDelayed(this, 1000); // Update every second
                }
            }, 1000); // Delay 1 second for the first update
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    // Restart the player from the beginning
                    mp.seekTo(0);
                    mp.start();

                    // stopPlayer();
                }
            });
        }

        player.start();
    }

    public void play6(View v) {
        if (player == null) {
            player = MediaPlayer.create(this, R.raw.ramdhun);
            textTotal.setText(formatTime(player.getDuration()));

            // seek bar progress code
            seekBar.setMax(player.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    // Update the progress text view
                    textProgress.setText(formatTime(progress));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // Pause the player when the user starts seeking
                    pause(v);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // Seek to the selected position if player is not null
                    if (player != null) {
                        player.seekTo(seekBar.getProgress());
                        // Resume playing if it was playing before seeking
                        if (isPlaying) {
                            player.start();
                        }
                    }
                }
            });


            // Update SeekBar and textProgress automatically with song progress
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (player != null && player.isPlaying()) {
                        int currentPosition = player.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                        textProgress.setText(formatTime(currentPosition));
                    }
                    handler.postDelayed(this, 1000); // Update every second
                }
            }, 1000); // Delay 1 second for the first update
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                    // Restart the player from the beginning
                    mp.seekTo(0);
                    mp.start();

                    // stopPlayer();
                }
            });
        }

        player.start();
    }

    public void pause(View v) {
        if (player != null) {
            player.pause();
            pausedPosition = player.getCurrentPosition(); // Store the current position of the song
        }
    }

    public void stop(View v) {
        stopPlayer();
    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            // Toast.makeText(this, "MediaPlayer released", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }

    // play pause for music player
    public void playPause(View view) {
        if (player != null) {
            if (isPlaying) {
                // Change the image to "play_icon"
                button_play_pause.setImageResource(R.drawable.play_icon);
                pause(view);
            } else {
                // Change the image to "pause_icon"
                button_play_pause.setImageResource(R.drawable.pause_icon);
                player.seekTo(pausedPosition); // Set the MediaPlayer's position to the stored position
                player.start();
            }
            // Toggle the state
            isPlaying = !isPlaying;
        }
    }


    // play pause for music player
    public void sound_mute(View view) {
        if(player!=null){ // done to handle when no music is selected
            if (isMute) {
                // Change the image to "play_icon"
                button_mute_sound.setImageResource(R.drawable.sound_icon);
                player.setVolume(1f, 1f); // Set volume to full
            } else {
                // Change the image to "pause_icon"
                button_mute_sound.setImageResource(R.drawable.mute_icon);
                player.setVolume(0f, 0f); // Set volume to 0
            }
            // Toggle the state
            isMute = !isMute;
        }
    }

    // Method to format time in minutes and seconds
    private String formatTime(int millis) {
        int seconds = (millis / 1000) % 60;
        int minutes = (millis / (1000 * 60)) % 60;
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    // disabled back button to not to send user to login screen
    @Override
    public void onBackPressed() {
        // Do nothing (disable back button)

    }


    // Code for using relative layout as Drawer
    private void openDrawer() {
        customDrawerLayout.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(-DRAWER_WIDTH, 0, 0, 0);
        animate.setDuration(100);
        customDrawerLayout.startAnimation(animate);
        isDrawerOpen = true;
    }

    private void closeDrawer() {
        TranslateAnimation animate = new TranslateAnimation(0, -DRAWER_WIDTH, 0, 0);
        animate.setDuration(100);
        customDrawerLayout.startAnimation(animate);
        customDrawerLayout.setVisibility(View.GONE);
        isDrawerOpen = false;
    }
    // End of  Code for using relative layout as Drawer
}
// Class ends




