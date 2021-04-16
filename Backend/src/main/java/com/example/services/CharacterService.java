package com.example.services;

import com.example.model.*;
import com.example.model.Character;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.example.services.deletion.CharacterDeletionService;
import com.example.spring_data_repositories.CharacterRepository;
import com.example.spring_data_repositories.MovieRepository;
import com.example.spring_data_repositories.StarshipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CharacterService extends EntityService<Character, CharacterRepository, CharacterDeletionService> {

    private final MovieRepository movieRepository;

    private final StarshipRepository starshipRepository;

    public CharacterService(CharacterRepository repository, CharacterDeletionService deletionService,
                            MovieRepository movieRepository, StarshipRepository starshipRepository) {
        super(repository, deletionService);
        this.movieRepository = movieRepository;
        this.starshipRepository = starshipRepository;
    }

    public void save(Character objectToPersist) {
        getCharacterFromRequestObject(objectToPersist);
    }

    private void getCharacterFromRequestObject(Character character) {
        if (character instanceof DroidDTO) {
            // TODO The name of this method implies that it returns something. I'd also assume that it doesn't save the
            // character in the database, but it does. Rename it to something more descriptive.
            getDroidFromRequestDto(character);
        } else if (character instanceof HumanDTO) {
            getHumanFromRequestDto(character);
        } else if (!(character instanceof Human) && !(character instanceof Droid)) {
            throw new IllegalArgumentException(
                    character.getClass().getSimpleName() + " is not a supported subtype of Character");
        } else {
            repository.save(character);
        }
    }

    // TODO This method is far too long.
    Character createCharacterFromRequestDto(Character entity, Character requestDto) {
        entity = repository.save(entity);
        CharacterDTO dtoObject = (CharacterDTO) requestDto;
        List<Movie> appearsIn = new ArrayList<>();
        List<Long> movieIds = dtoObject.getMovieIds();

        List<Character> friends = new ArrayList<>();
        List<Long> friendIds = dtoObject.getFriendIds();

        for (Long id : friendIds) {
            Optional<Character> friend = repository.findById(id);
            if (friend.isEmpty()) {
                throw new IllegalArgumentException(String.format("Character with id: %d does not exist.", id));
            }

            /*
             * //This is purposely commented out so that you can see I managed to solve the 'friends' issue we
             * discussed. //However, if I try to get all the characters, I get an infinite recursion due to the nature
             * of the relationship. List<Character> friendsOfFriend = friend.getFriends();
             * friendsOfFriend.add(actualDroid); friend.setFriends(friendsOfFriend);
             */
            friends.add(friend.get());
        }

        for (Long id : movieIds) {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            if (optionalMovie.isEmpty()) {
                throw new IllegalArgumentException(String.format("Movie with id: %d does not exist.", id));
            }
            appearsIn.add(optionalMovie.get());
        }
        entity.setFriends(friends);
        entity.setAppearsIn(appearsIn);
        return entity;
    }

    private void getDroidFromRequestDto(Character dto) {
        DroidDTO dtoObject = (DroidDTO) dto;
        Droid actualDroid = Droid.parseDroid(dtoObject);
        createCharacterFromRequestDto(actualDroid, dto);
    }

    private void getHumanFromRequestDto(Character dto) {
        HumanDTO dtoObject = (HumanDTO) dto;
        Human actualHuman = Human.parseHuman(dtoObject);
        actualHuman = (Human) createCharacterFromRequestDto(actualHuman, dto);

        List<Starship> humanStarships = new ArrayList<>();
        List<Long> starshipIds = dtoObject.getStarshipsIds();

        // TODO This loop could be extracted in its own method.
        for (Long id : starshipIds) {
            Optional<Starship> optionalStarship = starshipRepository.findById(id);
            if (optionalStarship.isEmpty()) {
                throw new IllegalArgumentException(String.format("Starship with id: %d does not exist.", id));
            }
            humanStarships.add(optionalStarship.get());
        }

        actualHuman.setStarships(humanStarships);
    }

}
