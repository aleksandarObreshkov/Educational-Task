package com.example.commands.delete;

import com.example.app.clients.CharacterClient;
import com.example.app.commands.delete.DeleteCharacterCommand;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

public class DeleteCharacterCommandTest extends DeleteTest<DeleteCharacterCommand, CharacterClient>{

    @Override
    protected DeleteCharacterCommand setCommand(CharacterClient client) {
        return new DeleteCharacterCommand(client);
    }

    @Override
    protected CharacterClient createClient() {
        return Mockito.mock(CharacterClient.class);
    }

    @Override
    @Test
    public void validDeleteCommand() {
        validCommand((characterClient,id) -> verify(characterClient).delete(id));
    }
}
