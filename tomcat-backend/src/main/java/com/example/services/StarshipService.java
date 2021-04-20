package com.example.services;

import com.example.model.Starship;
import com.example.repositories.StarshipRepository;
import com.example.services.deletion.StarshipDeletionService;

public class StarshipService extends EntityService<Starship, StarshipRepository, StarshipDeletionService> {

    public StarshipService(StarshipRepository repository, StarshipDeletionService deletionService) {
        super(repository, deletionService);
    }
    public StarshipService(){
        this(new StarshipRepository(), new StarshipDeletionService());
    }

}
