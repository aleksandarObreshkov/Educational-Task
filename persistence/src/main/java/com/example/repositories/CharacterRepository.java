package com.example.repositories;

import com.example.model.Character;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CharacterRepository<T extends Character> extends EntityRepository<T>{

    @Override
    public List<T> findAll(Class<T> type) {
        String tableName = type.getSuperclass().getSimpleName();
        return execute(manager ->
                manager.createQuery(
                        //TODO: Also use JPQL query parameters instead of string concatenation. You shouldn't get used to
                        // concatenation, because it might lead to security vulnerabilities.
                        "SELECT a FROM "+tableName+" a WHERE TYPE(a) = "+type.getSimpleName(), type)
                .getResultList());
    }

    //TODO: Fix
    public List<Character> findAllCharacters(){
        return new ArrayList<>();
    }

}
