package com.example.app.printing.printers;

import com.example.app.printing.representers.CharacterMoviesRepresenter;
import com.example.model.Character;

public class CharacterMoviesPrinter extends EntityPrinter<Character, CharacterMoviesRepresenter> {

    public CharacterMoviesPrinter(CharacterMoviesRepresenter representer) {
        super(representer);
    }

    public CharacterMoviesPrinter() {
        this(new CharacterMoviesRepresenter());
    }
}
