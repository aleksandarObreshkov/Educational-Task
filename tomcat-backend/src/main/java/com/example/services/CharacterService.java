package com.example.services;

import com.example.model.*;
import com.example.model.Character;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;
import com.example.repositories.CharacterRepository;
import com.example.services.deletion.CharacterDeletionService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CharacterService extends EntityService<Character, CharacterRepository, CharacterDeletionService> {
    public CharacterService(CharacterRepository repository, CharacterDeletionService deletionService) {
        super(repository, deletionService);
    }

    public void save(Character objectToPersist){
        if (objectToPersist instanceof DroidDTO){
            objectToPersist= getDroidFromRequestDto(objectToPersist);
        }
        else if (objectToPersist instanceof HumanDTO){
            objectToPersist= getHumanFromRequestDto(objectToPersist);
        }
        else if (!(objectToPersist instanceof Human) && !(objectToPersist instanceof Droid)){
            throw new IllegalArgumentException(objectToPersist.getClass().getSimpleName()+" is not a supported subtype of Character");
        }
        repository.save(objectToPersist);
    }

    private void createCharacterFromRequestDto(Character entity, Character requestDto){
        EntityManager manager = repository.getEntityManager();
        CharacterDTO dtoObject = (CharacterDTO)requestDto;
        List<Movie> appearsIn = new ArrayList<>();
        List<Long> movieIds = dtoObject.getMovieIds();

        List<Character> friends = new ArrayList<>();
        List<Long> friendIds = dtoObject.getFriendIds();

        for (Long id : friendIds){
            Character friend = repository.findById(id);
            if (friend==null){
                throw new IllegalArgumentException(String.format("Character with id: %d does not exist.", id));
            }
            //This is purposely commented out so that you can see I managed to solve the 'friends' issue we discussed.
            //However, if I try to get all the characters, I get an infinite recursion due to the nature of the relationship.
                /*
                List<Character> friendsOfFriend = friend.getFriends();
                friendsOfFriend.add(actualDroid);
                friend.setFriends(friendsOfFriend);
                 */
            friends.add(friend);
        }

        for (Long id : movieIds){
            Movie movie = manager.find(Movie.class, id);
            appearsIn.add(movie);
        }
        entity.setFriends(friends);
        entity.setAppearsIn(appearsIn);
    }

    private Droid getDroidFromRequestDto(Character dto){
        DroidDTO dtoObject = (DroidDTO)dto;
        Droid actualDroid = Droid.parseDroid(dtoObject);
        createCharacterFromRequestDto(actualDroid, dto);
        return actualDroid;
    }

    private Human getHumanFromRequestDto(Character dto){
        EntityManager manager = repository.getEntityManager();
        HumanDTO dtoObject = (HumanDTO)dto;
        Human actualHuman = Human.parseHuman(dtoObject);
        createCharacterFromRequestDto(actualHuman, dto);

        List<Starship> humanStarships = new ArrayList<>();
        List<Long> starshipIds = dtoObject.getStarshipsIds();

        for (Long id : starshipIds){
            Starship starship = manager.find(Starship.class, id);
            humanStarships.add(starship);
        }

        actualHuman.setStarships(humanStarships);

        return actualHuman;
    }
}
