package com.example.app.clients;

import com.example.model.Starship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarshipClient extends StarWarsClient{

    public StarshipClient(String url) {
        super(url);
    }

    public void delete(Long id){
        template.delete( url+id);
    }

    public void create(Starship starship){
        template.postForObject(url, starship, Starship.class);
    }

    public List<Starship> list(){
        Starship[] starships = template.getForObject(url, Starship[].class);
        if (starships==null){
            return new ArrayList<>();
        }
        return Arrays.asList(starships);
    }
}
