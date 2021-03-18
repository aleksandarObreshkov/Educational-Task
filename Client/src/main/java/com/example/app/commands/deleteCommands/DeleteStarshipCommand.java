package com.example.app.commands.deleteCommands;
import com.example.app.commands.Command;
import com.example.app.errors.RestTemplateResponseErrorHandler;
import org.apache.commons.cli.Options;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class DeleteStarshipCommand implements Command {

    private final RestTemplate template;
    private final String url;

    public DeleteStarshipCommand(String url) {
        this.url=url;
        this.template = new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Override
    public void execute() {
        template.delete(url);
    }

    public static String getDescription(){
        return "Delete a Starship with the specified id";
    }

    public static String getCommandString(){
        return "delete-starship";
    }

    public static  Options getDeleteOptions(){
        final Options options = new Options();
        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }
}
