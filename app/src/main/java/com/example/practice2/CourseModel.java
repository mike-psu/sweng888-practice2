package com.example.practice2;

import java.io.Serializable;

// Course data model that is serializable
public class CourseModel implements Serializable {
    private final String title;
    private final String subtitle;
    private final int image;

    public CourseModel(String title, String subtitle, int image) {
        this.title = title;
        this.subtitle = subtitle;
        this.image = image;
    }


    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getImage() {
        return image;
    }
}
