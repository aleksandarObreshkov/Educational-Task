package com.example.app.commands.deleteCommands;
import com.example.app.commands.Command;
import org.apache.commons.cli.Options;
import org.springframework.web.client.RestTemplate;

public class DeleteStarshipCommand implements Command {

    private final RestTemplate template;
    private final String url;

    public DeleteStarshipCommand(String url) {
        this.url=url;
        template=new RestTemplate();
    }

    @Override
    public void execute() {
        template.delete(url);
    }

    public static  Options getDeleteOptions(){
        final Options options = new Options();
        options.addOption("id", true, "delete an item with the specified id");
        return options;
    }
}
