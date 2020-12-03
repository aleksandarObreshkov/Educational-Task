package com.example.app.commands;
import org.springframework.web.client.RestTemplate;

public class DeleteStarshipCommand implements Command{

    private String url = "http://localhost:8080/starships/";
    private RestTemplate template;

    public DeleteStarshipCommand(String id) {
        url += id;
        template=new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        template.delete(url);
    }
}
