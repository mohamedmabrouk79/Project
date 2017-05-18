package com.example.mohamed.project;

/**
 * Created by mohamed on 5/17/2017.
 */

public class Movie {
    private String id;

    private String name;
    private String image;
    public Movie(String id, String image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
