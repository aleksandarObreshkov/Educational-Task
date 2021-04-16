
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

@Entity
@Validate
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
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

    // TODO You should always override hashCode() when you override equals():
    // https://www.baeldung.com/java-equals-hashcode-contracts
    @Override
    public boolean equals(Object obj) {
        if (!Movie.class.equals(obj.getClass())){
            return false;
        }
        Movie movieToCompare = (Movie) obj;
        return this.getTitle().equals(movieToCompare.getTitle()) &&
                this.getRating().equals(movieToCompare.getRating()) &&
                this.getReleaseDate().equals(movieToCompare.getReleaseDate());
    }
}

