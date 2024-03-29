package com.example.ramjikisena.drawerfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.ramjikisena.Profile;
import com.example.ramjikisena.R;

public class ContactUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

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

        // To go from  Contact to Profile

        Button button = findViewById(R.id.HomeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(ContactUs.this, Profile.class);
                startActivity(intent);
            }
        });

        ImageView app_img = findViewById(R.id.app_img);
        TextView app_text = findViewById(R.id.app_name);

        app_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactUs.this, Profile.class);
                startActivity(intent);
            }
        });

        app_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactUs.this, Profile.class);
                startActivity(intent);
            }
        });

    }
}