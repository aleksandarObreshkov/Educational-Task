
package model;

import com.fasterxml.jackson.annotation.JsonSetter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Movie {

    private String id;

    @NotNull(message = "Please provide a title.")
    private String title;

    @NotNull(message = "Wrong date format.")
    @Past(message = "Please provide a date from the past.") //change message
    private LocalDate releaseDate;

    @PositiveOrZero(message = "Rating can't be negative.") //if rating is missing, it equals 0.0, fix?
    private float rating;

    public Movie(){}

    public Movie(String title, float rating, LocalDate releaseDate){
        this.id = UUID.randomUUID().toString();
        this.title= title;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public Movie(String id, String title, LocalDate releaseDate, float rating) {
        this.id = id;
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

