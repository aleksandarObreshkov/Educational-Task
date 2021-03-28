package com.example.app.printing.printers;
import com.example.app.printing.representers.MovieRepresenter;
import com.example.model.Movie;

public class MoviePrinter extends EntityPrinter<Movie, MovieRepresenter> {
    public MoviePrinter(MovieRepresenter representer) {
        super(representer);
    }

    public MoviePrinter(){
        this(new MovieRepresenter());
    }
}
