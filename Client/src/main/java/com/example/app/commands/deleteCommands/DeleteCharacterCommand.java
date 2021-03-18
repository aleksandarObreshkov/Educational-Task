package com.example.app.commands.deleteCommands;

import com.example.app.commands.Command;
import com.example.app.errors.RestTemplateResponseErrorHandler;
import org.apache.commons.cli.Options;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class DeleteCharacterCommand implements Command {

    private final String url;
    private final RestTemplate template;

    public DeleteCharacterCommand(String url) {
        this.url = url;
        this.template = new RestTemplateBuilder().errorHandler(new RestTemplateResponseErrorHandler()).build();
    }

    @Override
    public void execute() {
        // TODO This is done in every delete command. Extract it in an abstract class.
        template.delete(url);
    }

    public static String getDescription() {
        return "Delete a character with the specified id";
    }

    public static String getCommandString() {
        return "delete-character";
    }

    public static Options getDeleteOptions() {
        final Options options = new Options();
        // TODO This should be a description of the option and not of the command itself. Use "The id of the character"
        // or something similar.
        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }

}
