package com.example.practice2;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CourseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        // Obtain the course data model from the intent
        CourseModel selectedCourse = (CourseModel) getIntent().getSerializableExtra("selected_course");
        // Check if the course was obtained
        if (selectedCourse != null) {
            // Set the field references
            TextView textViewTitle = findViewById(R.id.textViewTitle);
            TextView textViewSubtitle = findViewById(R.id.textViewSubtitle);
            ImageView imageView = findViewById(R.id.imageView);
            Button buttonBack = findViewById(R.id.buttonBack);
            // Set the field values
            textViewTitle.setText(selectedCourse.getTitle());
            textViewSubtitle.setText(selectedCourse.getSubtitle());
            imageView.setImageResource(selectedCourse.getImage());
            // Handle the click event of the Back button
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Return to the main activity sending the course data model
                    returnToMain(selectedCourse);
                }
            });
            // Handle the previous navigation on the device
            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Return to the main activity sending the course data model
                    returnToMain(selectedCourse);
                }
            });
        } else {
            // Return to the main activity sending no information
            returnToMain(null);
        }
    }

    // Return to the main activity sending a result
    private void returnToMain(CourseModel selectedCourse) {
        // Check if the course was sent
        if (selectedCourse != null) {
            // Create an intent
            Intent resultIntent = new Intent();
            // Add the course data model to the intent
            resultIntent.putExtra("returned_course", selectedCourse);
            // Set the result was successful with the intent
            setResult(Activity.RESULT_OK, resultIntent);
            // Notify this activity is completed
            finish();
        } else {
            // Create an intent
            Intent resultIntent = new Intent();
            // Set the result was not successful with the intent
            setResult(Activity.RESULT_CANCELED, resultIntent);
            // Notify this activity is completed
            finish();
        }
    }
}