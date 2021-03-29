package com.example.commands.delete;

import com.example.app.clients.StarWarsClient;
import com.example.app.commands.Command;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiConsumer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class DeleteTest<T extends Command, R extends StarWarsClient> {

    private R client;
    private T command;
    private Long id;

    protected abstract T setCommand(R client);
    protected abstract R createClient();

    @BeforeEach
    public void setUp(){
        id=102L;
        client = createClient();
        command = setCommand(client);
    }

    @Test
    public abstract void validDeleteCommand();

    public void validCommand(BiConsumer<R, Long> verification){
        String[] arguments = new String[]{command.getCommandString(), "-id", id+""};
        command.execute(arguments);
        verification.accept(client, id);
    }

    @Test
    public void missingCharacterIdTest(){
        String[] arguments = new String[]{command.getCommandString(), id+""};
        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()->command.execute(arguments));
        assertEquals(thrownException.getMessage(), "Missing required option: id");
    }

    @Test
    public void invalidIdFormatTest(){
        String[] arguments = new String[]{command.getCommandString(), "-id", "w"};
        Throwable thrownException = assertThrows(IllegalArgumentException.class, ()->command.execute(arguments));
        assertEquals(thrownException.getMessage(), "For input string: \"w\"");
    }
}
