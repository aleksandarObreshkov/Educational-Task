package com.example.repositories;

import com.example.model.Character;

public class CharacterRepository extends EntityRepository<Character>{


    public CharacterRepository() {
        super(Character.class);
    }

}
