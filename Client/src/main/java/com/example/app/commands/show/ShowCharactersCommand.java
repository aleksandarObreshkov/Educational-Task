package com.example.app.commands.show;

import com.example.app.clients.CharacterClient;
import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import com.example.app.printing.printers.CharacterMoviesPrinter;
import com.example.app.printing.printers.CharacterPrinter;
import com.example.app.printing.printers.FriendsPrinter;
import com.example.app.printing.printers.HumanStarshipsPrinter;
import com.example.model.Character;
import com.example.model.Human;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;
import java.util.stream.Collectors;

public class ShowCharactersCommand extends Command {

    @Override
    public String getCommandString() {
        return "characters";
    }

    @Override
    public String getDescription() {
        return "Show all characters";
    }

    @Override
    public void execute(String[] arguments) {

        CharacterPrinter printer = new CharacterPrinter();
        FriendsPrinter friendsPrinter = new FriendsPrinter();
        HumanStarshipsPrinter humanStarshipsPrinter = new HumanStarshipsPrinter();
        CharacterMoviesPrinter characterMoviesPrinter = new CharacterMoviesPrinter();

        List<Character> characters = StarWarsClient.characters().list();
        printer.printTable(characters);
        friendsPrinter.printTable(characters);
        humanStarshipsPrinter.printTable(getAllHumans(characters));
        characterMoviesPrinter.printTable(characters);
    }

    private List<Human> getAllHumans(List<Character> characters){
        return characters.stream()
                .filter(character -> character.getClass().equals(Human.class))
                .map(character -> (Human)character)
                .collect(Collectors.toList());
    }

}
