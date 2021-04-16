package com.example.app.clients;

import com.example.model.Starship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StarshipClient extends StarWarsClient {

    public StarshipClient(String url) {
        super(url);
    }

    public void delete(Long id) {
        template.delete(url + id);
    }

    public void create(Starship starship) {
        template.postForObject(url, starship, Starship.class);
    }

    public List<Starship> list() {
        Starship[] starships = template.getForObject(url, Starship[].class);
        if (starships == null) {
            // TODO Collections.emptyList() is preferred over creating a new list. That's because it's very easy to get
            // in a situation where a method such as this one is called many times over and over (in a loop maybe) and
            // thousands of empty lists are created for nothing. Collections.emptyList() always returns the same
            // (unmodifiable) list instance, so it doesn't have the overhead of creating a new object.
            return new ArrayList<>();
        }
        return Arrays.asList(starships);
    }

}
