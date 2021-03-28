package com.example.repositories;

import com.example.model.Starship;

public class StarshipRepository extends EntityRepository<Starship> {

    public StarshipRepository() {
        super(Starship.class);
    }
}
