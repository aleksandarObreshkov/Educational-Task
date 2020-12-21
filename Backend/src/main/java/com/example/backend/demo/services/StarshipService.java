package com.example.backend.demo.services;

import com.example.backend.demo.repositories.EntityRepository;
import model.Starship;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;

@Service
public class StarshipService {

    private final EntityRepository repository;

    public StarshipService(EntityRepository repository) {
        this.repository = repository;
    }

    //unnecessary throws statement
    public List<Starship> getAll() throws IOException{
        return repository.findAll(Starship.class);
    }

    public Starship getStarshipById(String id){
        return repository.findById(Long.parseLong(id), Starship.class);
    }

    public void deleteStarshipById(String id){
        repository.deleteById(Long.parseLong(id), Starship.class);
    }

    public void addStarship(Starship starship){
        repository.save(starship);
    }
}
