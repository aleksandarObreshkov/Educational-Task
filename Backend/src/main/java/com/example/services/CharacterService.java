package com.example.services;

import com.example.model.*;
import com.example.model.Character;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.example.services.deletion.CharacterDeletionService;
import com.example.repositories.spring.CharacterRepository;
import com.example.repositories.spring.MovieRepository;
import com.example.repositories.spring.StarshipRepository;
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

    @Override
    public Character save(Character objectToPersist) {
        Character characterToPersist = getCharacterFromRequestObject(objectToPersist);
        return repository.save(characterToPersist);
    }

    private Character getCharacterFromRequestObject(Character character) {
        if (character instanceof DroidDTO) {
            return getDroidFromRequestDto(character);
        } else if (character instanceof HumanDTO) {
            return getHumanFromRequestDto(character);
        } else if ((character instanceof Human) || (character instanceof Droid)) {
            return character;
        }
        throw new IllegalArgumentException(
                character.getClass().getSimpleName() + " is not a supported subtype of Character");
    }

    Character createCharacterFromRequestDto(Character entity, Character requestDto) {
        entity = repository.save(entity);
        CharacterDTO dtoObject = (CharacterDTO) requestDto;
        List<Long> friendIds = dtoObject.getFriendIds();
        List<Long> movieIds = dtoObject.getMovieIds();

        entity.setFriends(getFriends(friendIds));
        entity.setAppearsIn(getMovies(movieIds));
        return entity;
    }

    private Character getDroidFromRequestDto(Character dto) {
        DroidDTO dtoObject = (DroidDTO) dto;
        Droid actualDroid = Droid.parseDroid(dtoObject);
        return createCharacterFromRequestDto(actualDroid, dto);
    }

    private Character getHumanFromRequestDto(Character dto) {
        HumanDTO dtoObject = (HumanDTO) dto;
        Human actualHuman = Human.parseHuman(dtoObject);
        actualHuman = (Human) createCharacterFromRequestDto(actualHuman, dto);

        List<Long> starshipIds = dtoObject.getStarshipsIds();
        actualHuman.setStarships(getStarships(starshipIds));
        return actualHuman;
    }

    private List<Starship> getStarships(List<Long> starshipIds){
        List<Starship> humanStarships = new ArrayList<>();
        for (Long id : starshipIds) {
            Optional<Starship> optionalStarship = starshipRepository.findById(id);
            if (optionalStarship.isEmpty()) {
                throw new IllegalArgumentException(String.format("Starship with id: %d does not exist.", id));
            }
            humanStarships.add(optionalStarship.get());
        }
        return humanStarships;
    }

    private List<Movie> getMovies(List<Long> movieIds){
        List<Movie> appearsIn = new ArrayList<>();
        for (Long id : movieIds) {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            if (optionalMovie.isEmpty()) {
                throw new IllegalArgumentException(String.format("Movie with id: %d does not exist.", id));
            }
            appearsIn.add(optionalMovie.get());
        }
        return appearsIn;
    }

    private List<Character> getFriends(List<Long> friendIds){

        List<Character> friends = new ArrayList<>();

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

        return friends;
    }

}
