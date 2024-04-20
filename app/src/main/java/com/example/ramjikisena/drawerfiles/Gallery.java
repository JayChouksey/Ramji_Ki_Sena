package com.example.ramjikisena.drawerfiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ramjikisena.GloryOfRamnaam;
import com.example.ramjikisena.ImportantTemples;
import com.example.ramjikisena.Mission;
import com.example.ramjikisena.Profile;
import com.example.ramjikisena.R;
import com.example.ramjikisena.WritingExperience;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Gallery extends AppCompatActivity {

    ViewPager viewPager;
    int images[] = {R.drawable.ayodhya_ramji_photo, R.drawable.about_img, R.drawable.gallery_img};
    com.example.ramjikisena.drawerfiles.ImageSliderAdapter myCustomPagerAdapter;
    int currentPage = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

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

        // To go from Mission to Important Temples
        TextView temples = findViewById(R.id.ImpTemples);
        temples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(Gallery.this, ImportantTemples.class);
                startActivity(intent);
            }
        });

        // To go from Mission to Mission
        TextView mission = findViewById(R.id.ourMission);
        mission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(Gallery.this, Mission.class);
                startActivity(intent);
            }
        });

        // To go from Mission to Glory of Ram Naam
        TextView ramNaamGlory = findViewById(R.id.gloryOfRamNaam);
        ramNaamGlory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(Gallery.this, GloryOfRamnaam.class);
                startActivity(intent);
            }
        });

        // To go from Mission to Writing Experience
        TextView write = findViewById(R.id.writingExperience);
        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(Gallery.this, WritingExperience.class);
                startActivity(intent);
            }
        });

        // To go from  Gallery to Profile

        Button button = findViewById(R.id.HomeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(Gallery.this, Profile.class);
                startActivity(intent);
            }
        });

        ImageView app_img = findViewById(R.id.app_img);
        TextView app_text = findViewById(R.id.app_name);

        app_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Profile.class);
                startActivity(intent);
            }
        });

        app_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Gallery.this, Profile.class);
                startActivity(intent);
            }
        });


        // End of Switching from one activity to another code

        // -------------------------------------------------------------------------------------------------------------

        // Image Sliding Code
        viewPager = findViewById(R.id.viewPager);
        myCustomPagerAdapter = new ImageSliderAdapter(this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

        final int NUM_PAGES = images.length;

        // Auto sliding the images
        final android.os.Handler handler = new android.os.Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 1500, 1500); // Change the time interval here (3000ms = 3 seconds)

    }
}