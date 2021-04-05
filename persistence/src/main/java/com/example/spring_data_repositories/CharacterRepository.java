package com.example.spring_data_repositories;

import com.example.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {
    Character deleteCharacterById(Long id);
}
