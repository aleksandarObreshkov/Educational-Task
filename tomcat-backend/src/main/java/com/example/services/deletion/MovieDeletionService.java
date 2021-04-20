package com.example.services.deletion;

import com.example.model.Character;
import com.example.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieDeletionService extends DeletionService<Movie> {

    public MovieDeletionService() {
        super();
    }

    @Override
    public void unlink(Movie entity) {
        List<Character> allCharacters = repository.findAll();
        for (Character character : allCharacters){
            character.getAppearsIn().remove(entity);
            repository.save(character);
        }
    }
}
