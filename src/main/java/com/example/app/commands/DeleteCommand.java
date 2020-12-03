package com.example.app.commands;

import org.springframework.web.client.RestTemplate;

public class DeleteCommand implements Command{

    private RestTemplate template;
    private String url;
    private String entityToDelete;
    private String idToDelete;

    public DeleteCommand(String url, String entityToDelete, String idToDelete) {
        this.template = new RestTemplate();
        this.url = url;
        this.entityToDelete = entityToDelete;
        this.idToDelete = idToDelete;
        makeUrl(url, entityToDelete,idToDelete);
    }

    private void makeUrl(String currentUrl, String entityToDelete, String idToDelete){
        if (entityToDelete.contains("movie")) url = currentUrl+"movies/"+idToDelete;
        else if (entityToDelete.contains("starship")) url = currentUrl+"starships/"+idToDelete;
        else if (entityToDelete.contains("character")) url = currentUrl+"characters/"+idToDelete;
    }

    @Override
    public void execute() throws Exception {
        template.delete(url);
    }
}
