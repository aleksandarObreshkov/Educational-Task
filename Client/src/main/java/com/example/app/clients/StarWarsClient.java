package com.example.app.clients;

import com.example.app.errors.RestTemplateResponseErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.util.InstanceFilter;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

public abstract class StarWarsClient {

    // Tomcat url: http://localhost:9090/tomcat_backend_war_exploded/
    // Spring url: http://localhost:8080/
    // CF url: http://starwars.cfapps.sap.hana.ondemand.com/
    protected RestTemplate template = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler())
            .build();
    protected final String url;

    public StarWarsClient(String url) {
        String backendApiUrl = getUrlEndpointFromEnvironmentVariable();
        this.url = backendApiUrl+url;
    }

    public static MovieClient movies() {
        return new MovieClient("movies/");
    }

    public static CharacterClient characters() {
        return new CharacterClient("characters/");
    }

    public static StarshipClient starships() {
        return new StarshipClient("starships/");
    }

    private String getUrlEndpointFromEnvironmentVariable(){
        String urlEnvVariable = System.getenv("BACKEND");
        switch (urlEnvVariable){
            case "tomcat": return "http://localhost:9090/tomcat_backend_war_exploded/";
            case "cf": return "http://starwars.cfapps.sap.hana.ondemand.com/";
            default: return "http://localhost:8080/";
        }
    }

}
