package com.example.app.clients;

import com.example.app.errors.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public abstract class StarWarsClient {

    // Tomcat url: http://localhost:9090/tomcat_backend_war_exploded/
    // Spring url: http://localhost:8080/
    protected RestTemplate template = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler())
            .build();
    // TODO Make this configurable via an environment variable (BACKEND_URL or something similar), so that you can show
    // the entire demo without having to recompile the client. Then you should also check whether the URL set by the
    // user ends with a slash '/' and add one if not.
    private static final String URL_ENDPOINT = "http://localhost:9090/tomcat_backend_war_exploded/";
    protected final String url;

    public StarWarsClient(String url) {
        this.url = url;
    }

    public StarWarsClient() {
        this(URL_ENDPOINT);
    }

    public static MovieClient movies() {
        return new MovieClient(URL_ENDPOINT + "movies/");
    }

    public static CharacterClient characters() {
        return new CharacterClient(URL_ENDPOINT + "characters/");
    }

    public static StarshipClient starships() {
        return new StarshipClient(URL_ENDPOINT + "starships/");
    }

}
