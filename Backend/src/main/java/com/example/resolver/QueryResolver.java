package com.example.resolver;

import com.example.resolver.character.DroidResolver;
import com.example.resolver.character.HumanResolver;
import com.example.resolver.movie.MovieResolver;
import com.example.resolver.starship.StarshipResolver;
import com.example.model.*;
import com.example.model.Character;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    private final StarshipResolver starshipResolver;
    private final MovieResolver movieResolver;
    private final DroidResolver droidResolver;
    private final HumanResolver humanResolver;

    public QueryResolver(StarshipResolver starshipResolver, MovieResolver movieResolver, DroidResolver droidResolver,
                         HumanResolver humanResolver) {
        this.starshipResolver = starshipResolver;
        this.movieResolver = movieResolver;
        this.droidResolver = droidResolver;
        this.humanResolver = humanResolver;
    }

    public Iterable<Movie> allMovies() {
        return movieResolver.allMovies();
    }

    public Optional<Movie> movie(Long id) {
        return movieResolver.movie(id);
    }

    public Iterable<Starship> allStarships() {
        return starshipResolver.allStarships();
    }

    public Optional<Starship> starship(Long id) {
        return starshipResolver.starship(id);
    }

    public Iterable<Character> allCharacters() {
        return droidResolver.allCharacters();
    }

    public Optional<Droid> droid(Long id) {
        return null;//droidResolver.characterWithIdAndType(id);
    }

    public Iterable<Droid> allDroids() {
        return null;//droidResolver.allDroids();
    }

    public Optional<Human> human(Long id) {
        return humanResolver.human(id);
    }

    public Iterable<Human> allHumans() {
        return humanResolver.allHumans();
    }

}
