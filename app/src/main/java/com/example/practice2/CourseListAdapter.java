package com.example.practice2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CourseListAdapter extends ArrayAdapter<CourseModel> {

    public CourseListAdapter(@NonNull Context context, @NonNull ArrayList<CourseModel> courses) {
        super(context, 0, courses);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            // Set the layout fragment for the view of the item
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_course_item, parent, false);
        }
        // Obtain the course data model for the item
        CourseModel course = getItem(position);
        // Check if the course was obtained
        if (course != null) {
            // Set the field references
            TextView textViewTitle = convertView.findViewById(R.id.title);
            TextView textViewSubtitle = convertView.findViewById(R.id.subtitle);
            ImageView imageView = convertView.findViewById(R.id.imageViewItem);
            // Set the field values
            textViewTitle.setText(course.getTitle());
            textViewSubtitle.setText(course.getSubtitle());
            imageView.setImageResource(course.getImage());
        }
        // Return the view of the item
        return convertView;
    }
}
