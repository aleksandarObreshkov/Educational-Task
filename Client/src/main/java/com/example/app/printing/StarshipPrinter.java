package com.example.app.printing;

import model.Starship;

import java.util.List;

public class StarshipPrinter {

    public void printStarshipsTable(List<Starship> starships){
        String[][] starshipTable = createStarshipTableArray(starships);
        TablePrinter.printDataTable(starshipTable);
    }
    private String[][] createStarshipTableArray(List<Starship> starships){
        String[][] starshipTable = new String[starships.size()+1][3];
        starshipTable[0][0] = "Id";
        starshipTable[0][1] = "Name";
        starshipTable[0][2] = "Length";

        for (int i = 1; i< starships.size()+1; i++){
            starshipTable[i][0] = starships.get(i-1).getId().toString();
            starshipTable[i][1] = starships.get(i-1).getName();
            starshipTable[i][2] = starships.get(i-1).getLength().toString();
        }
        return starshipTable;
    }
}
