
package com.example.model;

import com.example.processor.Validate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSetter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Validate
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @PositiveOrZero
    //added to show the annotation processing capabilities
    private Long id;

    @NotNull(message = "Please provide a title")
    @Column(unique = true, name = "title")
    private String title;

    @NotNull(message = "Wrong date format.")
    @Past(message = "Please provide a date from the past.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "release_date")
    private LocalDate releaseDate;

    @PositiveOrZero(message = "Rating can't be negative.")
    @NotNull(message = "Please provide a rating.")
    @Max(value = 10L, message = "Rating should be less than 10.")
    @Column(name = "rating")
    private Float rating;

    @JsonSetter
    public void setReleaseDate(String releaseDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.releaseDate = LocalDate.parse(releaseDate,formatter);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return Objects.equals(getId(), movie.getId()) && getTitle().equals(movie.getTitle()) && getReleaseDate().equals(movie.getReleaseDate()) && getRating().equals(movie.getRating());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getReleaseDate(), getRating());
    }
}

