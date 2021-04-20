package com.example.app.clients;

import com.example.model.Starship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StarshipClient extends EntityClient<Starship> {

    public StarshipClient(String url) {
        super(url, Starship.class);
    }

    @Override
    public List<Starship> list() {
        Starship[] starships = template.getForObject(url, Starship[].class);
        if (starships == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(starships);
    }

}
