package com.example.app.printing.representers;

import com.example.model.Starship;

import java.util.ArrayList;
import java.util.List;

public class StarshipRepresenter extends EntityRepresenter<Starship> {

    private final String unitOfMeasurement;

    public StarshipRepresenter(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    public StarshipRepresenter(){
        this("meters");
    }

    @Override
    public List<String> getHeaderRow() {
        return List.of("ID", "Name", "Length ("+unitOfMeasurement+")");
    }

    @Override
    public List<String> getRow(Starship entity) {
        List<String> values = new ArrayList<>();
        values.add(entity.getId().toString());
        values.add(entity.getName());
        values.add(entity.getLengthInMeters().toString());
        return values;
    }

}
