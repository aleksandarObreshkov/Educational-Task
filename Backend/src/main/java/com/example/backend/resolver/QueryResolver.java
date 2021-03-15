package com.example.backend.resolver;

import com.example.backend.resolver.character.DroidResolver;
import com.example.backend.resolver.character.HumanResolver;
import com.example.backend.resolver.movie.MovieResolver;
import com.example.backend.resolver.starship.StarshipResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import model.*;
import model.Character;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    private final StarshipResolver starshipResolver;
    private final MovieResolver movieResolver;
    private final DroidResolver droidResolver;
    private final HumanResolver humanResolver;

    public QueryResolver(StarshipResolver starshipResolver,
                         MovieResolver movieResolver,
                         DroidResolver droidResolver,
                         HumanResolver humanResolver) {
        this.starshipResolver = starshipResolver;
        this.movieResolver = movieResolver;
        this.droidResolver = droidResolver;
        this.humanResolver = humanResolver;
    }

    public Iterable<Movie> allMovies(){
        return movieResolver.allMovies();
    }

    public Optional<Movie> movie(Long id){
        return movieResolver.movie(id);
    }

    public Iterable<Starship> allStarships(){ return starshipResolver.allStarships(); }

    public Optional<Starship> starship(Long id){ return starshipResolver.starship(id); }

    public Iterable<Character> allCharacters() {
        return droidResolver.allCharacters();
    }

    public Optional<Droid> droid(Long id){
        return droidResolver.droid(id);
    }

    public Iterable<Droid> allDroids(){
        return droidResolver.allDroids();
    }

    public Optional<Human> human(Long id) {
        return humanResolver.human(id);
    }

    public Iterable<Human> allHumans(){
        return humanResolver.allHumans();
    }
}
