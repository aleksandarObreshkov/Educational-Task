package com.example.services;

import com.example.model.*;
import com.example.model.Character;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.example.repositories.CharacterRepository;
import com.example.repositories.MovieRepository;
import com.example.repositories.StarshipRepository;
import com.example.services.deletion.CharacterDeletionService;
import java.util.ArrayList;
import java.util.List;

public class CharacterService extends EntityService<Character, CharacterRepository, CharacterDeletionService> {

    private final MovieRepository movieRepository;
    private final StarshipRepository starshipRepository;

    public CharacterService(CharacterRepository repository, CharacterDeletionService deletionService) {
        super(repository, deletionService);
        movieRepository = new MovieRepository();
        starshipRepository = new StarshipRepository();
    }

    public void save(Character objectToPersist) {
        if (objectToPersist instanceof DroidDTO) {
            objectToPersist = getDroidFromRequestDto(objectToPersist);
        } else if (objectToPersist instanceof HumanDTO) {
            objectToPersist = getHumanFromRequestDto(objectToPersist);
        } else if (!(objectToPersist instanceof Human) && !(objectToPersist instanceof Droid)) {
            throw new IllegalArgumentException(
                    objectToPersist.getClass().getSimpleName() + " is not a supported subtype of Character");
        }
        repository.save(objectToPersist);
    }

    private void createCharacterFromRequestDto(Character entity, Character requestDto) {
        CharacterDTO dtoObject = (CharacterDTO) requestDto;
        List<Long> movieIds = dtoObject.getMovieIds();

        List<Long> friendIds = dtoObject.getFriendIds();

        entity.setFriends(getFriends(friendIds));
        entity.setAppearsIn(getMovies(movieIds));
    }

    private Droid getDroidFromRequestDto(Character dto) {
        DroidDTO dtoObject = (DroidDTO) dto;
        Droid actualDroid = Droid.parseDroid(dtoObject);
        createCharacterFromRequestDto(actualDroid, dto);
        return actualDroid;
    }

    private Human getHumanFromRequestDto(Character dto) {
        HumanDTO dtoObject = (HumanDTO) dto;
        Human actualHuman = Human.parseHuman(dtoObject);
        createCharacterFromRequestDto(actualHuman, dto);
        List<Long> starshipIds = dtoObject.getStarshipsIds();
        actualHuman.setStarships(getStarships(starshipIds));

        return actualHuman;
    }

    private List<Starship> getStarships(List<Long> starshipIds){
        List<Starship> humanStarships = new ArrayList<>();
        for (Long id : starshipIds) {
            Starship starship = starshipRepository.findById(id);
            if (starship==null) {
                throw new IllegalArgumentException(String.format("Starship with id: %d does not exist.", id));
            }
            humanStarships.add(starship);
        }
        return humanStarships;
    }

    private List<Movie> getMovies(List<Long> movieIds){
        List<Movie> appearsIn = new ArrayList<>();
        for (Long id : movieIds) {
            Movie movie = movieRepository.findById(id);
            if (movie==null) {
                throw new IllegalArgumentException(String.format("Movie with id: %d does not exist.", id));
            }
            appearsIn.add(movie);
        }
        return appearsIn;
    }

    private List<Character> getFriends(List<Long> friendIds){

        List<Character> friends = new ArrayList<>();

        for (Long id : friendIds) {
            Character friend = repository.findById(id);
            if (friend==null) {
                throw new IllegalArgumentException(String.format("Character with id: %d does not exist.", id));
            }

            /*
             * //This is purposely commented out so that you can see I managed to solve the 'friends' issue we
             * discussed. //However, if I try to get all the characters, I get an infinite recursion due to the nature
             * of the relationship. List<Character> friendsOfFriend = friend.getFriends();
             * friendsOfFriend.add(actualDroid); friend.setFriends(friendsOfFriend);
             */
            friends.add(friend);
        }

        return friends;
    }

}
