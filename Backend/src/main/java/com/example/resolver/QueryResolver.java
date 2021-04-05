package com.example.resolver;

import com.example.resolver.character.CharacterResolver;
import com.example.resolver.movie.MovieResolver;
import com.example.resolver.starship.StarshipResolver;
import com.example.model.*;
import com.example.model.Character;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryResolver implements GraphQLQueryResolver{

    private final StarshipResolver starshipResolver;
    private final MovieResolver movieResolver;
    private final CharacterResolver characterResolver;

    public QueryResolver(StarshipResolver starshipResolver, MovieResolver movieResolver, CharacterResolver characterResolver) {
        this.starshipResolver = starshipResolver;
        this.movieResolver = movieResolver;
        this.characterResolver = characterResolver;
    }

    public Iterable<Movie> allMovies() {
        return movieResolver.all();
    }

    public Optional<Movie> movie(Long id) {
        return movieResolver.entityWithId(id);
    }

    public Iterable<Starship> allStarships() {
        return starshipResolver.all();
    }

    public Optional<Starship> starship(Long id) {
        return starshipResolver.entityWithId(id);
    }

    public Iterable<Character> allCharacters() {
        return characterResolver.allCharacters();
    }

}
