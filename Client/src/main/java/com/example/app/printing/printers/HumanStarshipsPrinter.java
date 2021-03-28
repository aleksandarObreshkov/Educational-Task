package com.example.app.printing.printers;

import com.example.app.printing.representers.HumanStarshipRepresenter;
import com.example.model.Human;

public class HumanStarshipsPrinter extends EntityPrinter<Human, HumanStarshipRepresenter> {
    public HumanStarshipsPrinter(HumanStarshipRepresenter representer) {
        super(representer);
    }

    public HumanStarshipsPrinter(){
        this(new HumanStarshipRepresenter());
    }
}
