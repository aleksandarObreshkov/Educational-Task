package com.example.repositories.spring;

import com.example.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    int deleteCharacterById(Long id);
}
