package com.example.app.commands;

import model.Human;
import org.apache.commons.cli.CommandLine;
import model.Character;
import org.springframework.web.client.RestTemplate;

public class AddCharacterCommand implements Command {

    private RestTemplate template;
    private CommandLine cmd;
    private String url;

    public AddCharacterCommand(CommandLine cmd, String url){
        this.cmd = cmd;
        this.url = url;
        this.template = new RestTemplate();
    }

    @Override
    public void execute() throws Exception {
        try {
            Character characterToAdd = CreateEntityFunctions.createCharacter(cmd);
            System.out.println(characterToAdd.getCharacterType());
            Class a=Character.class; // TODO: You're using a raw type. Your IDE should've warned you about it.
                                     // https://www.informit.com/articles/article.aspx?p=2861454
                                     // one more thing: the variable name 'a' does not mean anything
                                     // TODO: change it to a meaningful name
            if (characterToAdd.getCharacterType().equals("human")) a= Human.class; // TODO: The best practice for writing if statements is to wrap the body in a block:
                                                                                   // if (characterToAdd.getCharacterType().equals("human")) {
                                                                                   //     a = Human.class;
                                                                                   // }
            template.postForObject(url, characterToAdd, a); // TODO: You're using a raw type.
        } catch (NumberFormatException e) { // TODO: This is not the correct level of abstraction to handle this exception. CreateEntityFunctions.createCharacter is the
                                            // method that "knows" what the NumberFormatException means, so the exception should be caught and handled there.
            throw new IllegalArgumentException("Age should be an integer: "+e.getMessage(),e);
        }
    }
}
