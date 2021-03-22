package com.example.app.printing;

import com.example.model.Starship;
import java.util.ArrayList;
import java.util.List;

public class StarshipPrinter {

    public void printStarshipsTable(List<Starship> starships){
        Table starshipTable = createStarshipTable(starships);
        TablePrinter.printDataTable(starshipTable);
    }

    private Table createStarshipTable(List<Starship> starships){
        StarshipRepresenter representer = new StarshipRepresenter();
        Table starshipTable = new Table(representer.getHeaderRow());
        for (Starship starship : starships){
            starshipTable.getRows().add(representer.getRow(starship));
        }
        return starshipTable;
    }

    private class StarshipRepresenter{
        public List<String> getHeaderRow() {
            return List.of("ID", "Name", "Length");
        }

        public List<String> getRow(Starship starship) {
            List<String> values = new ArrayList<>();
            values.add(starship.getId().toString());
            values.add(starship.getName());
            values.add(starship.getLength().toString());
            return values;
        }
    }

}

