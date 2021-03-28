package com.example.app.printing.printers;

import com.example.app.printing.representers.StarshipRepresenter;
import com.example.model.Starship;

public class StarshipPrinter extends EntityPrinter<Starship, StarshipRepresenter> {
    public StarshipPrinter(String unitOfMeasurement) {
        super(new StarshipRepresenter(unitOfMeasurement));
    }

    public StarshipPrinter(){
        super(new StarshipRepresenter());
    }
}
