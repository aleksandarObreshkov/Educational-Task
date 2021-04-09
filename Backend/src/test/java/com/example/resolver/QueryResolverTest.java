package com.example.resolver;
import com.example.resolver.character.CharacterResolver;
import com.example.resolver.movie.MovieResolver;
import com.example.resolver.starship.StarshipResolver;
import com.example.model.*;
import com.example.model.Character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryResolverTest {

    private QueryResolver resolver;

    @Mock
    public CharacterResolver characterResolver;
    @Mock
    public MovieResolver movieResolver;
    @Mock
    public StarshipResolver starshipResolver;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        resolver = new QueryResolver(starshipResolver, movieResolver, characterResolver);
    }

    @Test
    public void allMoviesTest(){
        Iterable<Movie> movies = new ArrayList<>();
        when(movieResolver.all()).thenReturn(movies);
        assertEquals(resolver.allMovies(), movies);
    }

    @Test
    public void allStarshipsTest(){
        Iterable<Starship> starships = new ArrayList<>();
        when(starshipResolver.all()).thenReturn(starships);
        assertEquals(resolver.allStarships(), starships);
    }

    @Test
    public void allCharacters(){
        List<Character> characters = new ArrayList<>();
        when(characterResolver.all()).thenReturn(characters);
        assertEquals(resolver.allCharacters(), characters);
    }

    @Test
    public void movie(){
        Movie movie = Mockito.mock(Movie.class);
        when(movieResolver.entityWithId(10L)).thenReturn(Optional.of(movie));
        assertEquals(resolver.movie(10L), Optional.of(movie));
    }

    @Test
    public void starship(){
        Starship starship = Mockito.mock(Starship.class);
        when(starshipResolver.entityWithId(10L)).thenReturn(Optional.of(starship));
        assertEquals(resolver.starship(10L), Optional.of(starship));
    }

    @Test
    public void character(){
        Character character = Mockito.mock(Character.class);
        when(characterResolver.entityWithId(10L)).thenReturn(Optional.of(character));
        assertEquals(resolver.character(10L), Optional.of(character));
    }


}
