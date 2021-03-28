package com.example.app.clients;

import com.example.app.errors.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public abstract class StarWarsClient {

    protected RestTemplate template = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
    private static final String URL_ENDPOINT = "http://localhost:8080/";
    protected final String url;

    protected StarWarsClient(String url) {
        this.url = url;
    }

    public static MovieClient movies(){
        return new MovieClient(URL_ENDPOINT +"movies/");
    }

    public static CharacterClient characters(){
        return new CharacterClient(URL_ENDPOINT +"characters/");
    }

    public static StarshipClient starships(){
        return new StarshipClient(URL_ENDPOINT +"starships/");
    }

}
