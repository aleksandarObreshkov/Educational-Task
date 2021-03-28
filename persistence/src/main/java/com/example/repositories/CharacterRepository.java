package com.example.repositories;

import com.example.model.*;
import com.example.model.Character;
import com.example.model.dto.CharacterDTO;
import com.example.model.dto.DroidDTO;
import com.example.model.dto.HumanDTO;;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

public class CharacterRepository<T extends Character> extends EntityRepository<T>{

    public CharacterRepository(Class<T> tClass) {
        super(tClass);
        type = tClass;
    }

    public List<T> findAllOfType() {
        return execute(manager ->
                manager.createQuery(
                        "SELECT a FROM Character a WHERE TYPE(a) = "+type.getSimpleName(), type)
                .getResultList());
    }

    public List<Character> findAllCharacters(){
        return execute(manager ->
                manager.createQuery(
                        "SELECT a FROM Character a ", Character.class)
                        .getResultList());
    }

    @Override
    public void save(T objectToPersist) {
        executeInTransaction(manager -> {
            if (objectToPersist.getClass().equals(DroidDTO.class)){
                Droid characterToPersist = getDroidFromRequestDto(objectToPersist, manager);
                manager.persist(characterToPersist);
            }
            else if (objectToPersist instanceof HumanDTO){
                Human characterToPersist = getHumanFromRequestDto(objectToPersist, manager);
                manager.persist(characterToPersist);
            }
            throw new IllegalArgumentException(objectToPersist.getClass().getSimpleName()+" is not a supported subtype of Character");
        });
    }

    private void createCharacterFromRequestDto(Character entity, T requestDto, EntityManager manager){
        CharacterDTO dtoObject = (CharacterDTO)requestDto;
        List<Movie> appearsIn = new ArrayList<>();
        List<Long> movieIds = dtoObject.getMovieIds();

        List<Character> friends = new ArrayList<>();
        List<Long> friendIds = dtoObject.getFriendIds();

        for (Long id : friendIds){
            Character friend = manager.find(requestDto.getClass(), id);
            //This is purposely commented out so that you can see that I managed to solve the 'friends' issue we discussed.
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

    private Droid getDroidFromRequestDto(T dto, EntityManager manager){
            DroidDTO dtoObject = (DroidDTO)dto;
            Droid actualDroid = Droid.parseDroid(dtoObject);
            createCharacterFromRequestDto(actualDroid, dto, manager);
            return actualDroid;
    }

    private Human getHumanFromRequestDto(T dto, EntityManager manager){
        HumanDTO dtoObject = (HumanDTO)dto;
        Human actualHuman = Human.parseHuman(dtoObject);
        createCharacterFromRequestDto(actualHuman, dto, manager);

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
