package com.example.services;

import com.example.model.Starship;
import com.example.services.deletion.StarshipDeletionService;
import com.example.repositories.spring.StarshipRepository;
import org.springframework.stereotype.Service;

@Service
public class StarshipService extends EntityService<Starship, StarshipRepository, StarshipDeletionService> {
    public StarshipService(StarshipRepository repository, StarshipDeletionService deletionService) {
        super(repository, deletionService);
    }
}
