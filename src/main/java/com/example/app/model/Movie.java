package com.example.app.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Movie {

    private String id;
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;
    private float rating;

    public Movie(){}

    public Movie(String title, LocalDate releaseDate, float rating) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }

    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }
    @JsonSetter
    public void setTitle(String title) {
        this.title = title;
    }
    @JsonSetter
    public void setReleaseDate(String releaseDate) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.releaseDate = LocalDate.parse(releaseDate,f);
    }
    @JsonSetter
    public void setRating(float rating) {
        this.rating = rating;
    }

    public Movie(String id,String title, LocalDate releaseDate, float rating) {
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

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public float getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                " title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                '}';
    }
}
