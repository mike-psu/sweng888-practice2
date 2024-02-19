package com.example.practice2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize the adapter for the ListView and load it with data
        CourseListAdapter adapter = new CourseListAdapter(MainActivity.this, loadCourseData());
        // Set the list view reference
        ListView listViewCourses = findViewById(R.id.listview_courses);
        // Set the adapter on the list view
        listViewCourses.setAdapter(adapter);
        // Register a request to start an activity for result using an intent
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // If it as successful returning from the course details activity
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Obtain the data from the returned intent
                        Intent data = result.getData();
                        // Check if the data is populated
                        if (data != null) {
                            // Obtain the course data model from the result data
                            CourseModel returnedCourse = (CourseModel) data.getSerializableExtra("returned_course");
                            // Check if the course was obtained
                            if (returnedCourse != null) {
                                // Create a Snackbar
                                Snackbar snackbar =  Snackbar.make(findViewById(R.id.constraintLayout), getString(R.string.ReturnSuccess, returnedCourse.getTitle()), Snackbar.LENGTH_LONG);
                                // Obtain the view of the Snackbar
                                View snackbarView = snackbar.getView();
                                // Obtain the TextView from the Snackbar view
                                TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                // Add the image of the course to the Snackbar message
                                textView.setCompoundDrawablesWithIntrinsicBounds(null, null, resizeImage(returnedCourse.getImage()), null);
                                // Show the Snackbar
                                snackbar.show();
                                return;
                            }
                        }
                    }
                    // Provide an error message to the user it was not successful returning from the course details activity
                    Snackbar.make(findViewById(R.id.constraintLayout), getText(R.string.ReturnFailure), Snackbar.LENGTH_LONG).show();
                }
            });
        listViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtain the course data model from the list item
                CourseModel selectedCourse = (CourseModel) parent.getItemAtPosition(position);
                // Create an intent to the course details activity
                Intent intent = new Intent(MainActivity.this, CourseDetailsActivity.class);
                // Add the course data model to the intent
                intent.putExtra("selected_course", selectedCourse);
                // Launch the intent
                activityResultLauncher.launch(intent);
            }
        });
    }

    // Populate the courses to be used in the list view
    private ArrayList<CourseModel> loadCourseData() {
        ArrayList<CourseModel> courses = new ArrayList<>();
        courses.add(new CourseModel("SWENG 585", "Design Patterns", R.drawable.psu_logo1));
        courses.add(new CourseModel("SWENG 586", "Requirements Engineering", R.drawable.psu_logo2));
        courses.add(new CourseModel("SWENG 587", "Software Systems Architecture", R.drawable.psu_logo3));
        courses.add(new CourseModel("SWENG 837", "Software Systems Design", R.drawable.psu_logo4));
        courses.add(new CourseModel("SWENG 861", "Software Construction", R.drawable.psu_logo5));
        return courses;
    }

    // Resize the image used in the Snackbar
    private BitmapDrawable resizeImage(int resourceImageId) {
        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceImageId);
        int desiredHeightPx = Math.round(60 * resources.getDisplayMetrics().density);
        return new BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, Math.round(desiredHeightPx * ((float) bitmap.getWidth() / bitmap.getHeight())), desiredHeightPx, false));
    }
}