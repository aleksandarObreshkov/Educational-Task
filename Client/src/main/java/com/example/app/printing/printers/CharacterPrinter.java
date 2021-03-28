package com.example.app.printing.printers;

import com.example.app.printing.representers.CharacterRepresenter;
import com.example.model.Character;

public class CharacterPrinter extends EntityPrinter<Character, CharacterRepresenter> {
    public CharacterPrinter(CharacterRepresenter representer) {
        super(representer);
    }

    public CharacterPrinter(){
        this(new CharacterRepresenter());
    }
}
