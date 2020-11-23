package com.example.app.domain;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.Date;

public class Movie {
    private String id;
    private String title;
    private Date releaseDate;
    private float rating;

    public Movie(){}

    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }
    @JsonSetter
    public void setTitle(String title) {
        this.title = title;
    }
    @JsonSetter
    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
    @JsonSetter
    public void setRating(float rating) {
        this.rating = rating;
    }

    public Movie(String id,String title, Date releaseDate, float rating) {
        this.id=id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }
}
