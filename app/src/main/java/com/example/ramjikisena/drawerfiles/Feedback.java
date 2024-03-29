package com.example.ramjikisena.drawerfiles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ramjikisena.Profile;
import com.example.ramjikisena.R;
import com.example.ramjikisena.Register;

public class Feedback extends AppCompatActivity {

    private EditText editTextFeedback;
    private EditText editTextName;
    private EditText editTextContact;
    private Button buttonSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

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

        editTextFeedback = findViewById(R.id.editTextFeedback);
        editTextName = findViewById(R.id.editTextName);
        editTextContact = findViewById(R.id.editTextContact);
        buttonSend = findViewById(R.id.buttonSend);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback = editTextFeedback.getText().toString().trim();
                String name = editTextName.getText().toString().trim();
                String contact = editTextContact.getText().toString().trim();

                String message = "Name: " + name + "\n\nContact: " + contact + "\n\nFeedback: " + feedback;

                if (!feedback.isEmpty() && !name.isEmpty() && !contact.isEmpty()) {
                    // Send message to WhatsApp
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse("https://api.whatsapp.com/send?phone=+918770994162&text=" + message));
                    startActivity(whatsappIntent);

                    /*// Send message via Email
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "jay.inextets@gmail.com", null));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Help and Support");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, helpMessage);
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));*/
                }
                else{
                    Toast.makeText(Feedback.this, "Fill all details.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // To go from  Feedback to Profile
        Button button = findViewById(R.id.HomeButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the second activity
                Intent intent = new Intent(Feedback.this, Profile.class);
                startActivity(intent);
            }
        });

        ImageView app_img = findViewById(R.id.app_img);
        TextView app_text = findViewById(R.id.app_name);

        app_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Profile.class);
                startActivity(intent);
            }
        });

        app_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Feedback.this, Profile.class);
                startActivity(intent);
            }
        });
    }
}