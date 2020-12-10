package com.example.backend.demo.services;

import com.example.backend.demo.FileCreator;
import com.example.backend.demo.HelperMethods;
import model.Starship;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class StarshipService {

    private final FileCreator fileCreator = new FileCreator();

    public List<Starship> getAll() throws IOException{
        return HelperMethods.getDataFromFile(fileCreator.getFileStarships(), Starship.class);
    }

    public Starship getStarshipById(String id) throws IOException{
        List<Starship> ships = getAll();
        for (Starship a:ships) {
            if (a.getId().equals(id)) return a;
        }
        throw new IOException("No starship with the specified id.");
    }

    public void deleteStarshipById(String id) throws IOException{
        List<Starship> starships = getAll();
        int index;
        for(Starship a:starships){
            if (a.getId().equals(id)){
                index=starships.indexOf(a);
                starships.remove(index);
                HelperMethods.writeDataToFile(starships,fileCreator.getFileStarships());
                return;
            }
        }

        throw new IOException("No starship with the specified id.");

    }

    public void addStarship(Starship starship) throws IOException{
        List<Starship> starships = HelperMethods.getDataFromFile(fileCreator.getFileStarships(), Starship.class);
        starship.setId(UUID.randomUUID().toString());
        starships.add(starship);
        HelperMethods.writeDataToFile(starships,fileCreator.getFileStarships());
    }
}
