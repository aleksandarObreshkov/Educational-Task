
package model;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @NotNull(message = "Please provide a title.")
    private String title;

    @NotNull(message = "Wrong date format.")
    @Past(message = "Please provide a date from the past.")
    private LocalDate releaseDate;

    @PositiveOrZero(message = "Rating can't be negative.") //if rating is missing, it equals 0.0, fix?
    private float rating;

    public Movie(){}

    public Movie(String the_force_awakens, float v, LocalDate parse) {
    }

    @JsonSetter
    public void setReleaseDate(String releaseDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.releaseDate = LocalDate.parse(releaseDate,formatter);
    }

    @Override
    public String toString() {
        return "Movie{" +
                " title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                '}';
    }
    //equals, hashCode

}

