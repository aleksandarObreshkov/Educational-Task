package com.example.backend.demo.graphQL;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class GraphQLDataFetchers{

    private static final List<Map<String, String>> movies = Arrays.asList(
            ImmutableMap.of("id", "movie1",
                    "title", "Harry Potter and the Philosopher's Stone",
                    "releaseDate", "1999-12-01",
                    "rating", "9.9"),
            ImmutableMap.of("id", "movie2",
                    "title", "Moby Dick",
                    "releaseDate", "2019-08-09",
                    "rating", "6.7")
    );

    private static final List<Map<String, String>> characters = Arrays.asList(
            ImmutableMap.of("id", "movie1",
                    "title", "Harry Potter and the Philosopher's Stone",
                    "releaseDate", "1999-12-01",
                    "rating", "9.9"),
            ImmutableMap.of("id", "movie2",
                    "title", "Moby Dick",
                    "releaseDate", "2019-08-09",
                    "rating", "6.7")
    );


    public DataFetcher getMovieByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String movieId = dataFetchingEnvironment.getArgument("id");
            return movies
                    .stream()
                    .filter(movie -> movie.get("id").equals(movieId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getCharacterByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String characterId = dataFetchingEnvironment.getArgument("id");
            return characters
                    .stream()
                    .filter(movie -> movie.get("id").equals(characterId))
                    .findFirst()
                    .orElse(null);
        };
    }

}
