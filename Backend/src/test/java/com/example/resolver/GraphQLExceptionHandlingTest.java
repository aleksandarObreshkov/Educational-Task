package com.example.resolver;

import com.example.errors.NotFoundException;
import com.example.resolver.character.DroidResolver;
import com.example.resolver.character.HumanResolver;
import com.example.resolver.movie.MovieResolver;
import com.example.resolver.starship.StarshipResolver;
import com.example.model.Droid;
import com.example.model.Human;
import com.example.model.Movie;
import com.example.model.Starship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.repositories.CharacterRepository;
import com.example.repositories.EntityRepository;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class GraphQLExceptionHandlingTest {

    @Mock
    @Qualifier("entityRepository'")
    public EntityRepository repository;

    @Mock
    @Qualifier("characterRepository'")
    public CharacterRepository characterRepository;

    public HumanResolver humanResolver;
    public DroidResolver droidResolver;
    public MovieResolver movieResolver;
    public StarshipResolver starshipResolver;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        movieResolver=new MovieResolver(repository);
        starshipResolver = new StarshipResolver(repository);
        humanResolver = new HumanResolver(characterRepository);
        droidResolver = new DroidResolver(characterRepository);
    }

    @Test
    public void movie(){
        when(repository.findById(10L, Movie.class)).thenReturn(null);
        Throwable thrownException = assertThrows(NotFoundException.class, () -> movieResolver.movie(10L));
        assert(thrownException.getMessage().contains("No Movie with the specified id."));
    }

    @Test
    public void starship(){
        when(repository.findById(10L, Starship.class)).thenReturn(null);
        Throwable thrownException = assertThrows(NotFoundException.class, () -> starshipResolver.starship(10L));
        assert(thrownException.getMessage().contains("No Starship with the specified id."));
    }

    @Test
    public void human(){
        when(characterRepository.findById(10L, Human.class)).thenReturn(null);
        Throwable thrownException = assertThrows(NotFoundException.class, () -> humanResolver.human(10L));
        assert(thrownException.getMessage().contains("No Human with the specified id."));
    }

    @Test
    public void droid(){
        when(characterRepository.findById(10L, Droid.class)).thenReturn(null);
        Throwable thrownException = assertThrows(NotFoundException.class, () -> droidResolver.droid(10L));
        assert(thrownException.getMessage().contains("No Droid with the specified id."));
    }
}
