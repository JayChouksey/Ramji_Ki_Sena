package com.example.ramjikisena;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WritingExperience extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing_experience);

        // checking from which activity it came:
        Intent intent = getIntent();
        String check = intent.getStringExtra("checkPrev") ;


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
            // To go from WritingExperience to Important Temples
            TextView temples = findViewById(R.id.ImpTemples);
            temples.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(WritingExperience.this, ImportantTemples.class);
                    intent.putExtra("checkPrev", check);
                    startActivity(intent);
                }
            });

            // To go from WritingExperience to Mission
            TextView mission = findViewById(R.id.ourMission);
            mission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(WritingExperience.this, Mission.class);
                    intent.putExtra("checkPrev", check);
                    startActivity(intent);
                }
            });

            // To go from WritingExperience to Glory of Ram Naam
            TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
            ramNaamGlory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(WritingExperience.this, GloryOfRamnaam.class);
                    intent.putExtra("checkPrev", check);
                    startActivity(intent);
                }
            });

            // To go from WritingExperience to Writing Experience
            TextView write = findViewById(R.id.writingExperience);
            write.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity
                    Intent intent = new Intent(WritingExperience.this, WritingExperience.class);
                    intent.putExtra("checkPrev", check);
                    startActivity(intent);
                }
            });

            // To go from Important Temples to Profile
            Button button = findViewById(R.id.HomeButton);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Start the second activity

                    if(check.equals("main")){
                        Intent intent2 = new Intent(WritingExperience.this, MainActivity.class);
                        startActivity(intent2);
                    }
                    if(check.equals("profile")){
                        Intent intent2 = new Intent(WritingExperience.this, Profile.class);
                        startActivity(intent2);
                    }
                    if(check.equals("register")){
                        Intent intent2 = new Intent(WritingExperience.this, Register.class);
                        startActivity(intent2);
                    }
                }
            });
        }

        // End of Switching from one activity to another code

        // ------------------------------------------------------------------------------------------------------------
    }
}