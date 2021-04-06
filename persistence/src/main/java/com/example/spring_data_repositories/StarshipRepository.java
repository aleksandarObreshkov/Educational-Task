package com.example.spring_data_repositories;

import com.example.model.Starship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarshipRepository extends JpaRepository<Starship, Long> {
    int deleteStarshipById(Long id);
}
