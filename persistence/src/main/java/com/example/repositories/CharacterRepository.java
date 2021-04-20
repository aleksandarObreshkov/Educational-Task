package com.example.repositories;

import com.example.model.Character;

import java.util.List;

public class CharacterRepository extends EntityRepository<Character>{
    public CharacterRepository() {
        super(Character.class);
    }

    @Override
    public List<Character> findAll() {
        return execute(
                manager ->{
                    List<Character> characters = manager.createQuery("SELECT DISTINCT a FROM " + type.getSimpleName() + " a LEFT JOIN FETCH a.appearsIn", type)
                            .getResultList();
                    characters = manager.createQuery("SELECT DISTINCT a FROM " + type.getSimpleName() + " a LEFT JOIN FETCH a.starships WHERE a IN :characters", type)
                            .setParameter("characters", characters)
                            .getResultList();

                    characters = manager.createQuery("SELECT DISTINCT a FROM " + type.getSimpleName() + " a LEFT JOIN FETCH a.friends WHERE a IN :characters", type)
                            .setParameter("characters", characters)
                            .getResultList();
                    return characters;
                }
        );
    }

    @Override
    public Character findById(Long id) {
        return execute(manager -> {
            List<Character> characters = manager.createQuery("SELECT DISTINCT a FROM " + type.getSimpleName() + " a  LEFT JOIN FETCH a.appearsIn", type)
                    .getResultList();
            characters = manager.createQuery("SELECT DISTINCT a FROM " + type.getSimpleName() + " a LEFT JOIN FETCH a.starships WHERE a IN :characters", type)
                    .setParameter("characters", characters)
                    .getResultList();
            characters = manager.createQuery("SELECT DISTINCT a FROM " + type.getSimpleName() + " a LEFT JOIN FETCH a.friends WHERE a.id="+id+" AND a IN :characters", type)
                    .setParameter("characters", characters)
                    .getResultList();
            if (characters.isEmpty()){
                return null;
            }
            return characters.get(0);
        });
    }
}
